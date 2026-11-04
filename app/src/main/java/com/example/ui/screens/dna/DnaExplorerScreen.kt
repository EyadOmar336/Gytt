package com.example.ui.screens.dna

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.Nucleotide
import com.example.ui.components.DnaDoubleHelixCanvas
import com.example.ui.components.GlassCard
import com.example.ui.components.NucleotideBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.GeneLabViewModel

@Composable
fun DnaExplorerScreen(
    viewModel: GeneLabViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val isAr = language == AppLanguage.ARABIC

    val dnaInput by viewModel.dnaInput.collectAsState()
    val dnaAnalysis by viewModel.dnaAnalysis.collectAsState()

    val presets = listOf(
        "ATGCGATACGCTTGA" to (if (isAr) "تسلسل قياسي مع كودون بدء وتوقف" else "Standard Start & Stop"),
        "ATGGTGCACCTGACT" to (if (isAr) "بداية سلسلة بيتا-جلوبين الطبيعية" else "Beta-Globin HbA Fragment"),
        "AUGUUCGAAUAG" to (if (isAr) "تسلسل أحماض أمينية متعددة" else "Multi-peptide Sequence")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Double-Helix Visualizer Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("dna_visualizer_card"),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f),
                glowing = true
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = BioEmerald.copy(alpha = 0.15f),
                            modifier = Modifier.border(1.dp, BioEmerald.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = if (isAr) "هيكل اللولب المزدوج الحيوي" else "Double Helix Molecular Model",
                                color = BioEmerald,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isAr) "استكشف بنية الحمض النووي والقواعد النيتروجينية" else "Explore DNA Strands & Nucleotide Bases",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (isAr) "سلسلتان متعاكستان ترتبطان بروابط هيدروجينية وفق قاعدة التكامل."
                            else "Two antiparallel strands connected by complementary hydrogen bonds.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Interactive animated double helix
                    Box(
                        modifier = Modifier
                            .size(100.dp, 130.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF060D1E))
                            .border(1.dp, NeonCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    ) {
                        DnaDoubleHelixCanvas(
                            modifier = Modifier.fillMaxSize(),
                            strandSequence = dnaInput.ifBlank { "ATGCGA" }
                        )
                    }
                }
            }
        }

        // Nucleotide Base Pair Legend & Chemical Rules
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (isAr) "قواعد التكامل النيتروجيني (Chargaff's Rules):" else "Complementary Base Pairing (Chargaff's Rules):",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // A - T Pair
                    GlassCard(
                        modifier = Modifier.weight(1f),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            NucleotideBadge(base = 'A', size = 28.dp)
                            Text(text = "═", color = NeonCyan, fontWeight = FontWeight.Bold)
                            NucleotideBadge(base = 'T', size = 28.dp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isAr) "رابطتان هيدروجينيتان (A = T)" else "2 Hydrogen Bonds (A = T)",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // G - C Pair
                    GlassCard(
                        modifier = Modifier.weight(1f),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            NucleotideBadge(base = 'G', size = 28.dp)
                            Text(text = "≡", color = NeonCyan, fontWeight = FontWeight.Bold)
                            NucleotideBadge(base = 'C', size = 28.dp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isAr) "3 روابط هيدروجينية (G ≡ C)" else "3 Hydrogen Bonds (G ≡ C)",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Sequence Input & Presets
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("dna_input_card"),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ) {
                Text(
                    text = if (isAr) "أدخل تسلسل الـ DNA لتحليله وترجمته:" else "Enter DNA Sequence for Analysis & Translation:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = dnaInput,
                    onValueChange = {
                        viewModel.dnaInput.value = it.uppercase()
                        viewModel.analyzeDna()
                    },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("dna_sequence_text_field"),
                    shape = RoundedCornerShape(10.dp),
                    trailingIcon = {
                        if (dnaInput.isNotEmpty()) {
                            IconButton(onClick = {
                                viewModel.dnaInput.value = ""
                                viewModel.analyzeDna()
                            }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Presets
                Text(
                    text = if (isAr) "أمثلة تسلسلات جاهزة:" else "Quick Preset Sequences:",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(presets) { (seq, label) ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .clickable {
                                    viewModel.dnaInput.value = seq
                                    viewModel.analyzeDna()
                                }
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                                Text(text = seq, fontFamily = FontFamily.Monospace, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeonCyan)
                                Text(text = label, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }

        // Analysis Results: Complementary Strand, mRNA, and Polypeptide
        dnaAnalysis?.let { res ->
            if (res.isValid) {
                // Interactive Sequence Base View
                item {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth().testTag("sequence_viewer_card"),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                        glowing = true
                    ) {
                        Text(
                            text = if (isAr) "السلسلة الأصلية والسلسلة المكملة (5' -> 3'):" else "Sense & Complementary Strands (5' -> 3'):",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = NeonCyan
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Visual Base Chips Row
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(res.originalDna.toList()) { c ->
                                NucleotideBadge(base = c, size = 38.dp, showSubtext = "5'")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isAr) "السلسلة المكملة (3' -> 5'):" else "Complementary Strand (3' -> 5'):",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(res.complementaryStrand.toList()) { c ->
                                NucleotideBadge(base = c, size = 38.dp, showSubtext = "3'")
                            }
                        }
                    }
                }

                // mRNA Transcription Result
                item {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Biotech, contentDescription = null, tint = ElectricViolet, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isAr) "نسخ الـ mRNA الرسول (استبدال T بـ U):" else "mRNA Transcription (T substituted with U):",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "5'- ${res.mrnaStrand} -3'",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                letterSpacing = 2.sp,
                                color = ElectricViolet,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }

                // Polypeptide Chain Translation
                item {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth().testTag("polypeptide_card"),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                        glowing = true
                    ) {
                        Text(
                            text = if (isAr) "ترجمة الكودونات إلى سلسلة عديد الببتيد (البروتين):"
                            else "Polypeptide Translation (Amino Acid Chain):",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = BioEmerald
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        if (res.codons.isEmpty()) {
                            Text(
                                text = if (isAr) "يلزم على الأقل 3 نيوكليوتيدات لتشكيل كودون واحد."
                                else "Need at least 3 nucleotides to form a codon.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(res.codons) { (codon, aa) ->
                                    val aaColor = if (aa != null) Color(aa.colorHex) else Color.Gray
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = aaColor.copy(alpha = 0.15f),
                                        modifier = Modifier
                                            .border(1.dp, aaColor.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = codon,
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = aaColor
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = aa?.shortCode ?: "???",
                                                fontWeight = FontWeight.Black,
                                                fontSize = 14.sp,
                                                color = Color.White
                                            )
                                            Text(
                                                text = if (isAr) aa?.nameAr ?: "غير محدد" else aa?.nameEn ?: "Unknown",
                                                fontSize = 9.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Error message
                item {
                    res.errorMessage?.let { msg ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = BioCoral.copy(alpha = 0.15f),
                            modifier = Modifier.fillMaxWidth().border(1.dp, BioCoral, RoundedCornerShape(10.dp))
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Error, contentDescription = null, tint = BioCoral)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = msg, fontSize = 12.sp, color = BioCoral)
                            }
                        }
                    }
                }
            }
        }
    }
}
