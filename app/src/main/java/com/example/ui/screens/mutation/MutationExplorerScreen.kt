package com.example.ui.screens.mutation

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.content.CuratedGeneticsData
import com.example.data.genetics.GeneticsEngine
import com.example.data.model.AppLanguage
import com.example.data.model.MutationType
import com.example.ui.components.GlassCard
import com.example.ui.components.NucleotideBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.GeneLabViewModel

@Composable
fun MutationExplorerScreen(
    viewModel: GeneLabViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val isAr = language == AppLanguage.ARABIC
    val selectedExample by viewModel.selectedMutationExample.collectAsState()

    val originalAnalysis = remember(selectedExample.originalDna) {
        GeneticsEngine.analyzeDnaSequence(selectedExample.originalDna)
    }
    val mutatedAnalysis = remember(selectedExample.mutatedDna) {
        GeneticsEngine.analyzeDnaSequence(selectedExample.mutatedDna)
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
                modifier = Modifier.fillMaxWidth().testTag("mutation_header_card"),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f),
                glowing = true
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(BioCoral.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.ChangeCircle, contentDescription = null, tint = BioCoral, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isAr) "مستكشف الطفرات الجينية" else "Genetic Mutation Explorer",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (isAr) "استبدال، إزاحة، وتأثير الطفرات على سلاسل الأحماض الأمينية"
                            else "Substitutions, frameshifts & molecular protein impacts",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Example Selector Chips
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (isAr) "اختر نموذج طفرة وراثية تعليمي:" else "Select Educational Mutation Case:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(CuratedGeneticsData.MUTATION_EXAMPLES) { ex ->
                        val isSelected = selectedExample.id == ex.id
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) BioCoral.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) BioCoral else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { viewModel.selectedMutationExample.value = ex }
                                .testTag("mutation_chip_${ex.id}")
                        ) {
                            Text(
                                text = if (isAr) ex.titleAr else ex.titleEn,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) BioCoral else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Mutation Type Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isAr) selectedExample.type.labelAr else selectedExample.type.labelEn,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = NeonCyan
                    )
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = BioCoral.copy(alpha = 0.15f),
                        modifier = Modifier.border(1.dp, BioCoral.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                    ) {
                        Text(
                            text = if (isAr) "موضع الطفرة: القاعدة ${selectedExample.mutatedPosition + 1}" else "Position: Base ${selectedExample.mutatedPosition + 1}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BioCoral,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (isAr) selectedExample.type.descAr else selectedExample.type.descEn,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 17.sp
                )
            }
        }

        // Comparative DNA Sequence View
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("mutation_comparison_card"),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                glowing = true
            ) {
                Text(
                    text = if (isAr) "مقارنة التسلسل الطبيعي (Wild-Type) مع الطافر (Mutated):"
                    else "Wild-Type vs Mutated Sequence Comparison:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = NeonCyan
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Original Wildtype
                Text(
                    text = if (isAr) "1. التسلسل الأصلي الطبيعي:" else "1. Wild-Type Original DNA:",
                    fontSize = 11.sp,
                    color = BioEmerald,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(selectedExample.originalDna.toList().mapIndexed { i, c -> Pair(i, c) }) { (idx, c) ->
                        val isMutatedSite = idx == selectedExample.mutatedPosition
                        NucleotideBadge(base = c, size = 32.dp, highlight = isMutatedSite)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Mutated DNA
                Text(
                    text = if (isAr) "2. التسلسل بعد حدوث الطفرة:" else "2. Mutated DNA Sequence:",
                    fontSize = 11.sp,
                    color = BioCoral,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(selectedExample.mutatedDna.toList().mapIndexed { i, c -> Pair(i, c) }) { (idx, c) ->
                        val isMutatedSite = idx == selectedExample.mutatedPosition
                        NucleotideBadge(base = c, size = 32.dp, highlight = isMutatedSite)
                    }
                }
            }
        }

        // Protein Polypeptide Comparison
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("polypeptide_comparison_card"),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ) {
                Text(
                    text = if (isAr) "أثر الطفرة على سلسلة الأحماض الأمينية (البروتين):"
                    else "Impact on Polypeptide Amino Acid Chain:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = ElectricViolet
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Wildtype Protein
                Text(
                    text = if (isAr) "سلسلة البروتين الأصلية:" else "Original Protein Chain:",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(originalAnalysis.codons) { (codon, aa) ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = BioEmerald.copy(alpha = 0.15f),
                            modifier = Modifier.border(1.dp, BioEmerald.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        ) {
                            Text(
                                text = "${aa?.shortCode ?: codon}",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = BioEmerald,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Mutated Protein
                Text(
                    text = if (isAr) "سلسلة البروتين الطافرة:" else "Mutated Protein Chain:",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(mutatedAnalysis.codons) { (codon, aa) ->
                        val isStop = codon in listOf("UAA", "UAG", "UGA")
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isStop) BioCoral.copy(alpha = 0.25f) else BioAmber.copy(alpha = 0.15f),
                            modifier = Modifier.border(
                                1.dp,
                                if (isStop) BioCoral else BioAmber.copy(alpha = 0.4f),
                                RoundedCornerShape(6.dp)
                            )
                        ) {
                            Text(
                                text = "${aa?.shortCode ?: codon}",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isStop) BioCoral else BioAmber,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Biological Consequence & Scientific Nuance
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                borderColor = BioCoral.copy(alpha = 0.35f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Biotech, contentDescription = null, tint = BioCoral, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isAr) "التأثير البيولوجي الدقيق للطفرة" else "Biological Mechanism & Impact",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isAr) selectedExample.biologicalImpactAr else selectedExample.biologicalImpactEn,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
