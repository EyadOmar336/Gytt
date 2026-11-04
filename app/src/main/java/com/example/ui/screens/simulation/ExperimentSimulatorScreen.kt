package com.example.ui.screens.simulation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.GlassCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.GeneLabViewModel

@Composable
fun ExperimentSimulatorScreen(
    viewModel: GeneLabViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val isAr = language == AppLanguage.ARABIC

    val sampleSize by viewModel.simSampleSize.collectAsState()
    val simParent1 by viewModel.simParent1.collectAsState()
    val simParent2 by viewModel.simParent2.collectAsState()
    val simResult by viewModel.simResult.collectAsState()
    val isSimulating by viewModel.isSimulating.collectAsState()

    val sampleSizes = listOf(10, 50, 100, 500, 1000)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("simulation_header_card"),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f),
                glowing = true
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(BioAmber.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Science, contentDescription = null, tint = BioAmber, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isAr) "مختبر محاكاة التزاوج الحيوي" else "Virtual Breeding Laboratory",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (isAr) "محاكاة مونت كارلو لمطابقة الواقع مع الاحتمال النظري" else "Monte Carlo stochastic breeding & Chi-Square test",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Experiment Setup: Parents & Sample Size
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ) {
                Text(
                    text = if (isAr) "إعدادات التجربة والعينات" else "Experiment Setup & Sample Size",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Parents row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = if (isAr) "الأب الأول (P1)" else "Parent 1 (P1)", fontSize = 11.sp, color = NeonCyan)
                        OutlinedTextField(
                            value = simParent1,
                            onValueChange = { if (it.length <= 2) viewModel.simParent1.value = it },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().testTag("sim_parent1_input")
                        )
                    }

                    Text(text = "×", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = if (isAr) "الأب الثاني (P2)" else "Parent 2 (P2)", fontSize = 11.sp, color = ElectricViolet)
                        OutlinedTextField(
                            value = simParent2,
                            onValueChange = { if (it.length <= 2) viewModel.simParent2.value = it },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().testTag("sim_parent2_input")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Sample Size Chooser
                Text(
                    text = if (isAr) "حجم عينة الأبناء (N):" else "Sample Size (N offspring):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(sampleSizes) { size ->
                        val isSelected = sampleSize == size
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) NeonCyan.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surface,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) NeonCyan else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    viewModel.simSampleSize.value = size
                                    viewModel.runBreedingSimulation()
                                }
                                .testTag("sample_size_$size")
                        ) {
                            Text(
                                text = "N = $size",
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = { viewModel.runBreedingSimulation() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonCyan,
                        contentColor = Color(0xFF00222B)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().testTag("run_simulation_button")
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isAr) "إجراء المحاكاة العشوائية الآن" else "Run Simulated Crosses Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Simulation Comparison Results
        simResult?.let { res ->
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth().testTag("simulation_results_card"),
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    glowing = true
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isAr) "مقارنة النتائج الفعلية مع المتوقعة" else "Observed vs Expected Outcomes",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = NeonCyan
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = BioEmerald.copy(alpha = 0.15f),
                            modifier = Modifier.border(1.dp, BioEmerald.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        ) {
                            Text(
                                text = "N = ${res.totalOffspring}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = BioEmerald,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Phenotype Comparisons
                    res.observedPhenotypes.forEach { (pheno, count) ->
                        val obsPct = res.observedPhenotypePercentages[pheno] ?: 0f
                        val expPct = res.expectedPhenotypePercentages[pheno] ?: 0f

                        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = pheno, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                Text(
                                    text = "$count (${String.format("%.1f", obsPct)}% vs Exp ${String.format("%.1f", expPct)}%)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            // Dual progress bar
                            LinearProgressIndicator(
                                progress = { obsPct / 100f },
                                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                                color = NeonCyan,
                                trackColor = MaterialTheme.colorScheme.surface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Chi Square Card
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "اختبار كاي تربيع (χ² Goodness-of-Fit)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = BioAmber
                                )
                                Text(
                                    text = "χ² = ${String.format("%.2f", res.chiSquareValue)}",
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 13.sp,
                                    color = BioAmber
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = res.pValueApprox,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Statistical Analysis Explanation
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    borderColor = NeonCyan.copy(alpha = 0.3f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isAr) "تفسير قانون الأعداد الكبيرة والانحراف العشوائي" else "Sampling Error & Law of Large Numbers",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isAr) res.analysisAr else res.analysisEn,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
