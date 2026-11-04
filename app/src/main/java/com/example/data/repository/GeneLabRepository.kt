package com.example.data.repository

import com.example.data.content.CuratedGeneticsData
import com.example.data.local.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GeneLabRepository(private val dao: GeneLabDao) {

    // Chat sessions
    val sessions: Flow<List<ChatSessionEntity>> = dao.getAllSessions()

    fun getMessages(sessionId: String): Flow<List<ChatMessageEntity>> =
        dao.getMessagesForSession(sessionId)

    suspend fun createNewSession(initialTitle: String = "Genetics Discussion"): String {
        val id = UUID.randomUUID().toString()
        val session = ChatSessionEntity(id = id, title = initialTitle)
        dao.insertSession(session)
        return id
    }

    suspend fun saveMessage(sessionId: String, role: String, content: String, isAi: Boolean = true) {
        val msg = ChatMessageEntity(
            sessionId = sessionId,
            role = role,
            content = content,
            isAiGenerated = isAi
        )
        dao.insertMessage(msg)
        dao.renameSession(sessionId, content.take(30).trim() + "...")
    }

    suspend fun renameSession(sessionId: String, newTitle: String) {
        dao.renameSession(sessionId, newTitle)
    }

    suspend fun deleteSession(sessionId: String) {
        dao.deleteMessagesForSession(sessionId)
        dao.deleteSession(sessionId)
    }

    // Experiments
    val experiments: Flow<List<ExperimentEntity>> = dao.getAllExperiments()

    suspend fun saveExperiment(
        title: String,
        type: String,
        parentGenotypes: String,
        summary: String,
        detailsJson: String = ""
    ): Long {
        return dao.insertExperiment(
            ExperimentEntity(
                title = title,
                experimentType = type,
                parentGenotypes = parentGenotypes,
                summary = summary,
                detailsJson = detailsJson
            )
        )
    }

    suspend fun deleteExperiment(id: Long) = dao.deleteExperiment(id)

    // Lessons
    val lessonProgress: Flow<Map<String, LessonProgressEntity>> =
        dao.getAllLessonProgress().map { list -> list.associateBy { it.lessonId } }

    suspend fun markLessonCompleted(lessonId: String, score: Int = 100) {
        dao.updateLessonProgress(
            LessonProgressEntity(
                lessonId = lessonId,
                isCompleted = true,
                score = score
            )
        )
    }

    // Quizzes
    val quizAttempts: Flow<List<QuizAttemptEntity>> = dao.getAllQuizAttempts()

    suspend fun recordQuizResult(topic: String, difficulty: String, score: Int, total: Int) {
        dao.insertQuizAttempt(
            QuizAttemptEntity(
                topic = topic,
                difficulty = difficulty,
                score = score,
                totalQuestions = total
            )
        )
    }

    // Bookmarks
    val bookmarks: Flow<List<BookmarkEntity>> = dao.getAllBookmarks()

    suspend fun toggleBookmark(type: String, itemId: String, titleEn: String, titleAr: String, subtitle: String = "") {
        if (dao.isBookmarked(type, itemId)) {
            dao.deleteBookmark(type, itemId)
        } else {
            dao.insertBookmark(
                BookmarkEntity(
                    itemType = type,
                    itemId = itemId,
                    titleEn = titleEn,
                    titleAr = titleAr,
                    subtitle = subtitle
                )
            )
        }
    }

    fun getAllLessons(): List<Lesson> = CuratedGeneticsData.LESSONS
    fun getAllGlossary(): List<GlossaryTerm> = CuratedGeneticsData.GLOSSARY
    fun getAllMutations(): List<MutationExample> = CuratedGeneticsData.MUTATION_EXAMPLES
    fun getQuestionBank(): List<QuizQuestion> = CuratedGeneticsData.FULL_QUESTION_BANK
}
