package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.GeneticsAiService
import com.example.data.content.CuratedGeneticsData
import com.example.data.genetics.GeneticsEngine
import com.example.data.local.BookmarkEntity
import com.example.data.local.ChatMessageEntity
import com.example.data.local.ChatSessionEntity
import com.example.data.local.ExperimentEntity
import com.example.data.local.GeneLabDatabase
import com.example.data.local.LessonProgressEntity
import com.example.data.local.QuizAttemptEntity
import com.example.data.model.*
import com.example.data.repository.GeneLabRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppNavDestination(val iconName: String, val titleEn: String, val titleAr: String) {
    DASHBOARD("dashboard", "Dashboard", "الرئيسية"),
    LAB_TOOLS("biotech", "Lab Tools", "المختبر"),
    PUNNETT_SIMULATOR("grid_on", "Punnett Square", "مربع بانيت"),
    MENDELIAN_SOLVER("calculate", "Genetics Solver", "محلل الوراثة"),
    DNA_EXPLORER("dna", "DNA Explorer", "مستكشف الـ DNA"),
    EXPERIMENT_SIMULATOR("science", "Breeding Lab", "محاكي التزاوج"),
    MUTATION_EXPLORER("change_circle", "Mutations", "مستكشف الطفرات"),
    LEARNING_CENTER("menu_book", "Lessons", "الدروس"),
    AI_QUIZ("quiz", "AI Quiz", "الاختبارات"),
    AI_ASSISTANT("psychology", "AI Assistant", "المساعد الذكي")
}

data class UiNotification(val message: String, val isError: Boolean = false)

class GeneLabViewModel(application: Application) : AndroidViewModel(application) {

    private val db = GeneLabDatabase.getDatabase(application)
    val repository = GeneLabRepository(db.dao())
    val aiService = GeneticsAiService()

    // Preferences & Navigation
    val language = MutableStateFlow(AppLanguage.ARABIC)
    val isDarkTheme = MutableStateFlow(true)
    val currentScreen = MutableStateFlow(AppNavDestination.DASHBOARD)
    val navigationStack = mutableListOf<AppNavDestination>(AppNavDestination.DASHBOARD)
    val activeNotification = MutableStateFlow<UiNotification?>(null)

    // Room Flows
    val sessions: StateFlow<List<ChatSessionEntity>> = repository.sessions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val experiments: StateFlow<List<ExperimentEntity>> = repository.experiments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val lessonProgress: StateFlow<Map<String, LessonProgressEntity>> = repository.lessonProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val quizAttempts: StateFlow<List<QuizAttemptEntity>> = repository.quizAttempts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.bookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Chat Assistant State
    val currentSessionId = MutableStateFlow<String?>(null)
    val currentMessages = MutableStateFlow<List<ChatMessageEntity>>(emptyList())
    val isAiThinking = MutableStateFlow(false)

    // Punnett Simulator State
    val monohybridP1 = MutableStateFlow("Aa")
    val monohybridP2 = MutableStateFlow("Aa")
    val selectedTrait = MutableStateFlow<TraitPreset?>(GeneticsEngine.DEFAULT_TRAITS[0])
    val isDihybridMode = MutableStateFlow(false)
    val dihybridP1 = MutableStateFlow("AaBb")
    val dihybridP2 = MutableStateFlow("AaBb")
    val punnettResult = MutableStateFlow<PunnettResult?>(null)

    // DNA Explorer State
    val dnaInput = MutableStateFlow("ATGCGATACGCTTGA")
    val dnaAnalysis = MutableStateFlow<GeneticsEngine.DnaAnalysisResult?>(null)

    // Experiment Simulator State
    val simSampleSize = MutableStateFlow(100)
    val simParent1 = MutableStateFlow("Aa")
    val simParent2 = MutableStateFlow("Aa")
    val simResult = MutableStateFlow<SimulationSummary?>(null)
    val isSimulating = MutableStateFlow(false)

    // Mutation Explorer State
    val selectedMutationExample = MutableStateFlow(CuratedGeneticsData.MUTATION_EXAMPLES[0])
    val customMutationDna = MutableStateFlow("ATGAAATTCGGC")
    val selectedMutationType = MutableStateFlow(MutationType.MISSENSE_SUBSTITUTION)

    // Learning Center State
    val selectedLesson = MutableStateFlow<Lesson?>(null)
    val glossarySearchQuery = MutableStateFlow("")
    val selectedGlossaryCategory = MutableStateFlow("All")

    // Quiz State
    val quizTopic = MutableStateFlow("All Topics")
    val quizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val currentQuestionIndex = MutableStateFlow(0)
    val selectedOptionIndex = MutableStateFlow<Int?>(null)
    val isAnswerChecked = MutableStateFlow(false)
    val quizScore = MutableStateFlow(0)
    val isQuizFinished = MutableStateFlow(false)
    val quizUserAnswers = MutableStateFlow<MutableMap<Int, Int>>(mutableMapOf())

    init {
        // Initialize Punnett calculation
        calculatePunnett()
        // Initialize DNA analysis
        analyzeDna()
        // Initialize simulation
        runBreedingSimulation()
        // Ensure default session
        initDefaultChatSession()
    }

    fun toggleLanguage() {
        language.value = if (language.value == AppLanguage.ARABIC) AppLanguage.ENGLISH else AppLanguage.ARABIC
    }

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }

    fun navigateTo(destination: AppNavDestination) {
        if (currentScreen.value != destination) {
            navigationStack.add(destination)
            currentScreen.value = destination
        }
    }

    fun navigateBack(): Boolean {
        if (navigationStack.size > 1) {
            navigationStack.removeAt(navigationStack.size - 1)
            currentScreen.value = navigationStack.last()
            return true
        } else if (currentScreen.value != AppNavDestination.DASHBOARD) {
            currentScreen.value = AppNavDestination.DASHBOARD
            navigationStack.clear()
            navigationStack.add(AppNavDestination.DASHBOARD)
            return true
        }
        return false
    }

    fun showNotification(message: String, isError: Boolean = false) {
        activeNotification.value = UiNotification(message, isError)
    }

    fun clearNotification() {
        activeNotification.value = null
    }

    // Punnett Logic
    fun calculatePunnett() {
        if (isDihybridMode.value) {
            punnettResult.value = GeneticsEngine.calculateDihybrid(dihybridP1.value, dihybridP2.value)
        } else {
            punnettResult.value = GeneticsEngine.calculateMonohybrid(
                monohybridP1.value,
                monohybridP2.value,
                selectedTrait.value
            )
        }
    }

    fun saveCurrentPunnettExperiment() {
        val res = punnettResult.value ?: return
        viewModelScope.launch {
            val title = if (isDihybridMode.value) {
                "Dihybrid Cross: ${dihybridP1.value} × ${dihybridP2.value}"
            } else {
                "${selectedTrait.value?.nameEn ?: "Monohybrid"}: ${monohybridP1.value} × ${monohybridP2.value}"
            }
            repository.saveExperiment(
                title = title,
                type = if (isDihybridMode.value) "PUNNETT_DIHYBRID" else "PUNNETT_MONO",
                parentGenotypes = if (isDihybridMode.value) "${dihybridP1.value} × ${dihybridP2.value}" else "${monohybridP1.value} × ${monohybridP2.value}",
                summary = res.explanationEn.take(150) + "..."
            )
            showNotification(if (language.value == AppLanguage.ARABIC) "تم حفظ التجربة في سجلك بنجاح!" else "Experiment saved successfully!")
        }
    }

    // DNA Explorer Logic
    fun analyzeDna() {
        dnaAnalysis.value = GeneticsEngine.analyzeDnaSequence(dnaInput.value)
    }

    // Simulation Logic
    fun runBreedingSimulation() {
        viewModelScope.launch {
            isSimulating.value = true
            val res = GeneticsEngine.runSimulation(
                p1 = simParent1.value,
                p2 = simParent2.value,
                sampleSize = simSampleSize.value,
                trait = selectedTrait.value
            )
            simResult.value = res
            isSimulating.value = false
        }
    }

    // Chat Assistant Logic
    private fun initDefaultChatSession() {
        viewModelScope.launch {
            val currentSessions = repository.sessions.first()
            val sid = if (currentSessions.isNotEmpty()) {
                currentSessions.first().id
            } else {
                repository.createNewSession("Genetics Overview")
            }
            selectSession(sid)
        }
    }

    fun selectSession(sessionId: String) {
        currentSessionId.value = sessionId
        viewModelScope.launch {
            repository.getMessages(sessionId).collect { msgs ->
                currentMessages.value = msgs
            }
        }
    }

    fun createNewChat() {
        viewModelScope.launch {
            val title = if (language.value == AppLanguage.ARABIC) "محادثة وراثة جديدة" else "New Genetics Chat"
            val newId = repository.createNewSession(title)
            selectSession(newId)
        }
    }

    fun deleteSession(sessionId: String) {
        viewModelScope.launch {
            repository.deleteSession(sessionId)
            val remaining = repository.sessions.first()
            if (remaining.isNotEmpty()) {
                selectSession(remaining.first().id)
            } else {
                createNewChat()
            }
        }
    }

    fun sendMessage(userText: String) {
        if (userText.isBlank()) return
        val sid = currentSessionId.value ?: return

        viewModelScope.launch {
            // Save user message
            repository.saveMessage(sid, "user", userText, isAi = false)
            isAiThinking.value = true

            // Build history for context
            val history = currentMessages.value.takeLast(6).map { it.role to it.content }

            val (aiReply, _) = aiService.generateResponse(
                prompt = userText,
                language = language.value,
                chatHistory = history
            )

            repository.saveMessage(sid, "assistant", aiReply, isAi = true)
            isAiThinking.value = false
        }
    }

    // Quiz Logic
    fun startNewQuiz(topic: String = "All Topics") {
        quizTopic.value = topic
        val bank = CuratedGeneticsData.FULL_QUESTION_BANK
        val filtered = if (topic == "All Topics" || topic == "جميع الموضوعات") {
            bank.shuffled().take(5)
        } else {
            bank.filter { it.topic.equals(topic, ignoreCase = true) }.shuffled().ifEmpty { bank.shuffled() }.take(5)
        }
        quizQuestions.value = filtered
        currentQuestionIndex.value = 0
        selectedOptionIndex.value = null
        isAnswerChecked.value = false
        quizScore.value = 0
        isQuizFinished.value = false
        quizUserAnswers.value = mutableMapOf()
    }

    fun selectQuizOption(index: Int) {
        if (!isAnswerChecked.value) {
            selectedOptionIndex.value = index
        }
    }

    fun submitQuizAnswer() {
        val selected = selectedOptionIndex.value ?: return
        val currentQ = quizQuestions.value.getOrNull(currentQuestionIndex.value) ?: return

        isAnswerChecked.value = true
        quizUserAnswers.value[currentQuestionIndex.value] = selected

        if (selected == currentQ.correctIndex) {
            quizScore.value += 1
        }
    }

    fun nextQuizQuestion() {
        val nextIdx = currentQuestionIndex.value + 1
        if (nextIdx < quizQuestions.value.size) {
            currentQuestionIndex.value = nextIdx
            selectedOptionIndex.value = null
            isAnswerChecked.value = false
        } else {
            // Quiz finished
            isQuizFinished.value = true
            viewModelScope.launch {
                repository.recordQuizResult(
                    topic = quizTopic.value,
                    difficulty = "Standard",
                    score = quizScore.value,
                    total = quizQuestions.value.size
                )
            }
        }
    }

    // Lesson Progress
    fun completeLesson(lesson: Lesson) {
        viewModelScope.launch {
            repository.markLessonCompleted(lesson.id, 100)
            showNotification(
                if (language.value == AppLanguage.ARABIC) "تهانينا! أكملت الدرس: ${lesson.titleAr}" else "Congratulations! Completed: ${lesson.titleEn}"
            )
        }
    }

    fun toggleBookmark(type: String, itemId: String, titleEn: String, titleAr: String, subtitle: String = "") {
        viewModelScope.launch {
            repository.toggleBookmark(type, itemId, titleEn, titleAr, subtitle)
        }
    }
}
