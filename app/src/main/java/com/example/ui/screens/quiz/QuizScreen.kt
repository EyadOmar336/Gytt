package com.example.ui.screens.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.QuizQuestion
import com.example.ui.components.GlassCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.GeneLabViewModel

@Composable
fun QuizScreen(
    viewModel: GeneLabViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val isAr = language == AppLanguage.ARABIC

    val questions by viewModel.quizQuestions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedOption by viewModel.selectedOptionIndex.collectAsState()
    val isChecked by viewModel.isAnswerChecked.collectAsState()
    val score by viewModel.quizScore.collectAsState()
    val isFinished by viewModel.isQuizFinished.collectAsState()
    val userAnswers by viewModel.quizUserAnswers.collectAsState()
    val quizTopic by viewModel.quizTopic.collectAsState()
    val quizAttempts by viewModel.quizAttempts.collectAsState()

    val topics = listOf(
        "All Topics" to (if (isAr) "جميع الموضوعات" else "All Topics"),
        "DNA" to (if (isAr) "الحمض النووي DNA" else "DNA & Molecular"),
        "Punnett" to (if (isAr) "الوراثة ومربعات بانيت" else "Mendel & Punnett"),
        "Complex" to (if (isAr) "الوراثة اللا مندلية" else "Complex Traits"),
        "Mutations" to (if (isAr) "الطفرات الجينية" else "Mutations"),
        "Biotech" to (if (isAr) "التكنولوجيا الحيوية" else "Biotech & CRISPR")
    )

    if (questions.isEmpty()) {
        LaunchedEffect(Unit) {
            viewModel.startNewQuiz("All Topics")
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("quiz_header_card"),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f),
                glowing = true
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(BioEmerald.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Quiz, contentDescription = null, tint = BioEmerald, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isAr) "مولّد الاختبارات والتقييم الذكي" else "AI Quiz Generator & Practice",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (isAr) "اختبارات تفاعلية فورية لترسيخ المفاهيم مع التفسير العلمي"
                            else "Interactive practice with instant biological explanations",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Topic Filter Row
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = if (isAr) "اختر موضوع الاختبار:" else "Select Quiz Domain:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(topics) { (topicKey, topicLabel) ->
                        val isSelected = quizTopic == topicKey
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) BioEmerald.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) BioEmerald else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { viewModel.startNewQuiz(topicKey) }
                        ) {
                            Text(
                                text = topicLabel,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) BioEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        if (!isFinished && questions.isNotEmpty()) {
            val currentQ = questions.getOrNull(currentIndex) ?: questions[0]
            val totalQ = questions.size

            // Quiz Progress & Score Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isAr) "السؤال ${currentIndex + 1} من $totalQ" else "Question ${currentIndex + 1} of $totalQ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = NeonCyan
                    )
                    Text(
                        text = if (isAr) "الدرجة الحالية: $score" else "Current Score: $score",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = BioEmerald
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / totalQ },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = NeonCyan,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            // Question Box
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth().testTag("quiz_question_card"),
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    glowing = true
                ) {
                    Text(
                        text = if (isAr) currentQ.questionAr else currentQ.questionEn,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Options list
                    val options = if (isAr) currentQ.optionsAr else currentQ.optionsEn
                    options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedOption == optIndex
                        val isCorrect = optIndex == currentQ.correctIndex

                        val borderCol = when {
                            isChecked && isCorrect -> BioEmerald
                            isChecked && isSelected -> BioCoral
                            isSelected -> NeonCyan
                            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        }

                        val bgCol = when {
                            isChecked && isCorrect -> BioEmerald.copy(alpha = 0.2f)
                            isChecked && isSelected -> BioCoral.copy(alpha = 0.2f)
                            isSelected -> NeonCyan.copy(alpha = 0.15f)
                            else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = bgCol,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, borderCol, RoundedCornerShape(10.dp))
                                .clickable { viewModel.selectQuizOption(optIndex) }
                                .testTag("quiz_option_$optIndex")
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(borderCol.copy(alpha = 0.2f))
                                        .border(1.dp, borderCol, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = ('A' + optIndex).toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = borderCol
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = optionText,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.weight(1f)
                                )
                                if (isChecked && isCorrect) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = BioEmerald, modifier = Modifier.size(20.dp))
                                } else if (isChecked && isSelected) {
                                    Icon(Icons.Default.Close, contentDescription = null, tint = BioCoral, modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Explanation Card (After Checking)
            if (isChecked) {
                item {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth().testTag("quiz_explanation_card"),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        borderColor = if (selectedOption == currentQ.correctIndex) BioEmerald.copy(alpha = 0.5f) else BioCoral.copy(alpha = 0.5f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (selectedOption == currentQ.correctIndex) Icons.Default.CheckCircle else Icons.Default.Info,
                                contentDescription = null,
                                tint = if (selectedOption == currentQ.correctIndex) BioEmerald else BioCoral,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (selectedOption == currentQ.correctIndex) {
                                    if (isAr) "إجابة صحيحة وممتازة! 🎉" else "Correct Answer! Well done! 🎉"
                                } else {
                                    if (isAr) "إجابة غير دقيقة! إليك التفسير العلمي:" else "Incorrect! Scientific explanation:"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = if (selectedOption == currentQ.correctIndex) BioEmerald else BioCoral
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isAr) currentQ.explanationAr else currentQ.explanationEn,
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Action Button: Submit or Next
            item {
                if (!isChecked) {
                    Button(
                        onClick = { viewModel.submitQuizAnswer() },
                        enabled = selectedOption != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BioEmerald,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("quiz_submit_button")
                    ) {
                        Text(if (isAr) "تأكيد الإجابة والتحقق" else "Submit Answer", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                } else {
                    Button(
                        onClick = { viewModel.nextQuizQuestion() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = Color(0xFF00222B)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("quiz_next_button")
                    ) {
                        Text(
                            text = if (currentIndex + 1 < totalQ) {
                                if (isAr) "السؤال التالي ➔" else "Next Question ➔"
                            } else {
                                if (isAr) "عرض النتيجة النهائية 🏆" else "See Final Results 🏆"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        } else if (isFinished) {
            // End of Quiz Summary & Mistake Review
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth().testTag("quiz_finish_card"),
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                    glowing = true
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(BioEmerald.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = BioEmerald, modifier = Modifier.size(36.dp))
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (isAr) "اكتمل الاختبار بنجاح!" else "Quiz Completed!",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (isAr) "الدرجة النهائية: $score من ${questions.size} (${(score.toFloat() / questions.size * 100).toInt()}%)"
                            else "Final Score: $score / ${questions.size} (${(score.toFloat() / questions.size * 100).toInt()}%)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.startNewQuiz(quizTopic) },
                            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color(0xFF00222B)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isAr) "إعادة الاختبار بأسئلة جديدة" else "Retake with New Questions", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Review of Answers
            item {
                Text(
                    text = if (isAr) "مراجعة الأسئلة والإجابات النموذجية:" else "Detailed Answer Review:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            items(questions.mapIndexed { idx, q -> Pair(idx, q) }) { (idx, q) ->
                val userAns = userAnswers[idx]
                val isCorrect = userAns == q.correctIndex

                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    borderColor = if (isCorrect) BioEmerald.copy(alpha = 0.4f) else BioCoral.copy(alpha = 0.4f)
                ) {
                    Text(
                        text = "${idx + 1}. ${if (isAr) q.questionAr else q.questionEn}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isAr) "إجابتك: ${q.optionsAr.getOrNull(userAns ?: -1) ?: "لم تتم الإجابة"}"
                        else "Your Answer: ${q.optionsEn.getOrNull(userAns ?: -1) ?: "Unanswered"}",
                        fontSize = 12.sp,
                        color = if (isCorrect) BioEmerald else BioCoral,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = if (isAr) "الإجابة الصحيحة: ${q.optionsAr[q.correctIndex]}"
                        else "Correct Answer: ${q.optionsEn[q.correctIndex]}",
                        fontSize = 12.sp,
                        color = BioEmerald,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isAr) q.explanationAr else q.explanationEn,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
