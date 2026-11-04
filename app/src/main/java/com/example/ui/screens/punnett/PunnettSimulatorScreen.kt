package com.example.ui.screens.punnett

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
import com.example.data.genetics.GeneticsEngine
import com.example.data.model.AppLanguage
import com.example.data.model.TraitPreset
import com.example.ui.components.GlassCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppNavDestination
import com.example.ui.viewmodel.GeneLabViewModel

@Composable
fun PunnettSimulatorScreen(
    viewModel: GeneLabViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val isAr = language == AppLanguage.ARABIC

    val isDihybrid by viewModel.isDihybridMode.collectAsState()
    val p1Mono by viewModel.monohybridP1.collectAsState()
    val p2Mono by viewModel.monohybridP2.collectAsState()
    val p1Di by viewModel.dihybridP1.collectAsState()
    val p2Di by viewModel.dihybridP2.collectAsState()
    val selectedTrait by viewModel.selectedTrait.collectAsState()
    val punnettResult by viewModel.punnettResult.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mode Selector: Monohybrid vs Dihybrid
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            viewModel.isDihybridMode.value = false
                            viewModel.calculatePunnett()
                        }
                        .testTag("tab_monohybrid"),
                    color = if (!isDihybrid) NeonCyan.copy(alpha = 0.2f) else Color.Transparent
                ) {
                    Text(
                        text = if (isAr) "تزاوج أحادي (صفة واحدة)" else "Monohybrid Cross (1 Trait)",
                        modifier = Modifier.padding(vertical = 10.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = if (!isDihybrid) FontWeight.Bold else FontWeight.Normal,
                        color = if (!isDihybrid) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            viewModel.isDihybridMode.value = true
                            viewModel.calculatePunnett()
                        }
                        .testTag("tab_dihybrid"),
                    color = if (isDihybrid) ElectricViolet.copy(alpha = 0.2f) else Color.Transparent
                ) {
                    Text(
                        text = if (isAr) "تزاوج ثنائي (صفتان - 16 خلية)" else "Dihybrid Cross (2 Traits)",
                        modifier = Modifier.padding(vertical = 10.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = if (isDihybrid) FontWeight.Bold else FontWeight.Normal,
                        color = if (isDihybrid) ElectricViolet else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Trait Preset Selector (Monohybrid only)
        if (!isDihybrid) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = if (isAr) "اختر الصفة المندلية (نبات البازلاء):" else "Select Mendelian Trait (Pea Plant):",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(GeneticsEngine.DEFAULT_TRAITS) { trait ->
                            val isSelected = selectedTrait?.id == trait.id
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) NeonCyan.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(
                                        1.dp,
                                        if (isSelected) NeonCyan else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable {
                                        viewModel.selectedTrait.value = trait
                                        viewModel.monohybridP1.value = "${trait.dominantAllele}${trait.recessiveAllele}"
                                        viewModel.monohybridP2.value = "${trait.dominantAllele}${trait.recessiveAllele}"
                                        viewModel.calculatePunnett()
                                    }
                            ) {
                                Text(
                                    text = if (isAr) trait.nameAr else trait.nameEn,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Parent Genotype Pickers
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ) {
                Text(
                    text = if (isAr) "الطرز الجينية للأبوين (P1 × P2)" else "Parental Genotypes (P1 × P2)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Parent 1
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isAr) "الأب الأول (P1)" else "Parent 1 (P1)",
                            fontSize = 12.sp,
                            color = NeonCyan,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = if (isDihybrid) p1Di else p1Mono,
                            onValueChange = {
                                if (isDihybrid) {
                                    if (it.length <= 4) {
                                        viewModel.dihybridP1.value = it
                                        viewModel.calculatePunnett()
                                    }
                                } else {
                                    if (it.length <= 2) {
                                        viewModel.monohybridP1.value = it
                                        viewModel.calculatePunnett()
                                    }
                                }
                            },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().testTag("parent1_input")
                        )
                    }

                    Text(
                        text = "×",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Parent 2
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isAr) "الأب الثاني (P2)" else "Parent 2 (P2)",
                            fontSize = 12.sp,
                            color = ElectricViolet,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = if (isDihybrid) p2Di else p2Mono,
                            onValueChange = {
                                if (isDihybrid) {
                                    if (it.length <= 4) {
                                        viewModel.dihybridP2.value = it
                                        viewModel.calculatePunnett()
                                    }
                                } else {
                                    if (it.length <= 2) {
                                        viewModel.monohybridP2.value = it
                                        viewModel.calculatePunnett()
                                    }
                                }
                            },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().testTag("parent2_input")
                        )
                    }
                }

                // Quick genotype preset buttons
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (!isDihybrid) {
                        val d = selectedTrait?.dominantAllele ?: 'A'
                        val r = selectedTrait?.recessiveAllele ?: 'a'
                        listOf("$d$r × $d$r", "$d$d × $r$r", "$d$r × $r$r", "$d$d × $d$r").forEach { combo ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable {
                                        val parts = combo.split(" × ")
                                        viewModel.monohybridP1.value = parts[0]
                                        viewModel.monohybridP2.value = parts[1]
                                        viewModel.calculatePunnett()
                                    }
                            ) {
                                Text(
                                    text = combo,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                                )
                            }
                        }
                    } else {
                        listOf("AaBb × AaBb", "AABB × aabb", "AaBb × aabb").forEach { combo ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable {
                                        val parts = combo.split(" × ")
                                        viewModel.dihybridP1.value = parts[0]
                                        viewModel.dihybridP2.value = parts[1]
                                        viewModel.calculatePunnett()
                                    }
                            ) {
                                Text(
                                    text = combo,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Punnett Square Visual Grid
        punnettResult?.let { res ->
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth().testTag("punnett_grid_card"),
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    glowing = true
                ) {
                    Text(
                        text = if (isAr) "مصفوفة مربع بانيت الناتجة" else "Generated Punnett Square Grid",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = NeonCyan
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    PunnettGridVisual(
                        p1Gametes = res.parent1Gametes,
                        p2Gametes = res.parent2Gametes,
                        grid = res.grid,
                        isDihybrid = isDihybrid
                    )
                }
            }

            // Genotypes & Phenotypes Statistics
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Genotypic Breakdown
                    GlassCard(
                        modifier = Modifier.weight(1f),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ) {
                        Text(
                            text = if (isAr) "النسب الجينية" else "Genotypes",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = NeonCyan
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        res.genotypePercentages.entries.sortedByDescending { it.value }.forEach { entry ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = entry.key,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "${entry.value.toInt()}% (${res.genotypeFrequencies[entry.key]}/${if (isDihybrid) 16 else 4})",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Phenotypic Breakdown
                    GlassCard(
                        modifier = Modifier.weight(1f),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ) {
                        Text(
                            text = if (isAr) "النسب المظهرية" else "Phenotypes",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = BioEmerald
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        res.phenotypePercentages.entries.forEach { entry ->
                            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = entry.key,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.sp,
                                        maxLines = 1,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "${entry.value.toInt()}%",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = BioEmerald
                                    )
                                }
                                LinearProgressIndicator(
                                    progress = { entry.value / 100f },
                                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                                    color = BioEmerald,
                                    trackColor = MaterialTheme.colorScheme.surface
                                )
                            }
                        }
                    }
                }
            }

            // Step-by-Step Biological Explanation
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    borderColor = NeonCyan.copy(alpha = 0.3f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isAr) "التفسير العلمي المندلي خطوة بخطوة" else "Step-by-Step Scientific Explanation",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isAr) res.explanationAr else res.explanationEn,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            // Action Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { viewModel.saveCurrentPunnettExperiment() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).testTag("save_experiment_button")
                    ) {
                        Icon(Icons.Default.BookmarkAdd, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isAr) "حفظ التجربة" else "Save Record", fontSize = 12.sp)
                    }

                    Button(
                        onClick = {
                            viewModel.simParent1.value = p1Mono
                            viewModel.simParent2.value = p2Mono
                            viewModel.runBreedingSimulation()
                            viewModel.navigateTo(AppNavDestination.EXPERIMENT_SIMULATOR)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = Color(0xFF00222B)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).testTag("simulate_breeding_button")
                    ) {
                        Icon(Icons.Default.Science, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isAr) "محاكاة التزاوج" else "Simulate Lab", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun PunnettGridVisual(
    p1Gametes: List<String>,
    p2Gametes: List<String>,
    grid: List<List<String>>,
    isDihybrid: Boolean
) {
    val colCount = p1Gametes.size + 1
    val cellSize = if (isDihybrid) 52.dp else 68.dp

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Header Row (Parent 1 Gametes)
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Corner placeholder
            Box(
                modifier = Modifier.size(cellSize),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "P1 ↘\nP2",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            p1Gametes.forEach { g ->
                Box(
                    modifier = Modifier
                        .size(cellSize)
                        .clip(RoundedCornerShape(8.dp))
                        .background(NeonCyan.copy(alpha = 0.15f))
                        .border(1.dp, NeonCyan.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = g,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        fontSize = if (isDihybrid) 13.sp else 16.sp,
                        color = NeonCyan
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Grid Rows
        p2Gametes.forEachIndexed { rowIndex, g2 ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 3.dp)
            ) {
                // Left Header (Parent 2 Gamete)
                Box(
                    modifier = Modifier
                        .size(cellSize)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ElectricViolet.copy(alpha = 0.15f))
                        .border(1.dp, ElectricViolet.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = g2,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        fontSize = if (isDihybrid) 13.sp else 16.sp,
                        color = ElectricViolet
                    )
                }

                // Result cells
                val rowResults = grid.getOrNull(rowIndex) ?: emptyList()
                rowResults.forEach { offspringGenotype ->
                    val hasDominant = offspringGenotype.any { it.isUpperCase() }
                    Box(
                        modifier = Modifier
                            .size(cellSize)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (hasDominant) BioEmerald.copy(alpha = 0.12f)
                                else BioCoral.copy(alpha = 0.12f)
                            )
                            .border(
                                1.dp,
                                if (hasDominant) BioEmerald.copy(alpha = 0.35f)
                                else BioCoral.copy(alpha = 0.35f),
                                RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = offspringGenotype,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = if (isDihybrid) 11.sp else 15.sp,
                            color = if (hasDominant) BioEmerald else BioCoral
                        )
                    }
                }
            }
        }
    }
}
