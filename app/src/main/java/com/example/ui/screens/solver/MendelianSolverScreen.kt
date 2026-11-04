package com.example.ui.screens.solver

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
import com.example.data.model.AppLanguage
import com.example.ui.components.GlassCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppNavDestination
import com.example.ui.viewmodel.GeneLabViewModel

data class GeneticsProblemPreset(
    val id: String,
    val titleEn: String,
    val titleAr: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val p1Genotype: String,
    val p2Genotype: String,
    val inheritancePatternEn: String,
    val inheritancePatternAr: String,
    val stepByStepSolutionEn: String,
    val stepByStepSolutionAr: String
)

val PROBLEM_PRESETS = listOf(
    GeneticsProblemPreset(
        id = "cystic_fibrosis",
        titleEn = "Cystic Fibrosis Carrier Test",
        titleAr = "حامل لمرض التليف الكيسي (صفة متنحية)",
        descriptionEn = "Two asymptomatic carrier parents for autosomal recessive Cystic Fibrosis plan to have a child. What is the chance their child will have the disease?",
        descriptionAr = "أبوان كلاهما حامل لجين التليف الكيسي المتنحي دون ظهور أعراض (هجين). ما احتمالية ولادة طفل مصاب بالمرض؟",
        p1Genotype = "Cc",
        p2Genotype = "Cc",
        inheritancePatternEn = "Autosomal Recessive (Allele C: normal, allele c: cystic fibrosis)",
        inheritancePatternAr = "وراثة جسمية متنحية (الأليل C سليم، والأليل c مصاب)",
        stepByStepSolutionEn = "1. Allele Identification: Let C = Normal allele (dominant), c = CF mutation (recessive).\n2. Parent Genotypes: Mother = Cc, Father = Cc.\n3. Punnett Cross: Gametes C, c x C, c -> 1 CC, 2 Cc, 1 cc.\n4. Genotypic Ratios: 25% CC (unaffected non-carrier), 50% Cc (unaffected carrier), 25% cc (affected).\n5. Clinical Outcome: 25% (1 in 4) chance the child will have Cystic Fibrosis; 75% chance of being symptom-free.",
        stepByStepSolutionAr = "1. تحديد الأليلات: نرمز للأليل السائد السليم بـ C، وأليل المرض المتنحي بـ c.\n2. الطرز الجينية للآباء: الأم = Cc (حاملة)، الأب = Cc (حامل).\n3. التزاوج في بانيت: الأمشاج (C, c) × (C, c) تنتج: 1 CC و 2 Cc و 1 cc.\n4. النسبة الجينية: 25% سليم تماماً، 50% حامل للجين سليم، 25% مصاب بالمرض.\n5. النتيجة السريرية: احتمال إصابة الطفل بالمرض هو 25% (1 من كل 4 أطفال)، بينما احتمال سلامته ظاهرياً هو 75%."
    ),
    GeneticsProblemPreset(
        id = "huntington",
        titleEn = "Huntington's Disease Inheritance",
        titleAr = "مرض هنتنغتون العصبي (صفة سائدة)",
        descriptionEn = "A person with one parent diagnosed with Huntington's disease (heterozygous Hh) marries a healthy partner (hh). What are the chances for their offspring?",
        descriptionAr = "شخص حامل لجين هنتنغتون السائد هجين (Hh) تزوج من شريكة سليمة متماثلة الأليلات (hh). ما هي احتمالات إصابة الأبناء؟",
        p1Genotype = "Hh",
        p2Genotype = "hh",
        inheritancePatternEn = "Autosomal Dominant (Allele H: Huntington's disease, allele h: normal)",
        inheritancePatternAr = "وراثة جسمية سائدة (الأليل H يسبب المرض، والأليل h سليم)",
        stepByStepSolutionEn = "1. Genetic Principle: In autosomal dominant disorders, a single mutant allele (H) causes the disorder.\n2. Parental Cross: Hh (affected heterozygous) x hh (unaffected homozygous).\n3. Offspring Distribution: 50% Hh (will develop Huntington's), 50% hh (normal, unaffected).\n4. Conclusion: Each child has an independent 50% risk of inheriting the mutant allele.",
        stepByStepSolutionAr = "1. المبدأ الوراثي: في الأمراض السائدة يكفي وجود أليل طافر واحد (H) لظهور المرض.\n2. التزاوج: شخص مصاب هجين (Hh) × شخص سليم نقي (hh).\n3. احتمالات الأبناء: 50% Hh (مصابون بهنتنغتون لاحقاً)، 50% hh (سليمون تماماً).\n4. الخلاصة: لكل طفل فرصة متساوية ومستقلة بنسبة 50% لوراثة الجين المسبب للمرض."
    ),
    GeneticsProblemPreset(
        id = "blood_abo",
        titleEn = "ABO Blood Group Codominance",
        titleAr = "فصائل الدم ABO والسيادة المشتركة",
        descriptionEn = "A mother with blood type A (heterozygous I^A i) and a father with blood type B (heterozygous I^B i) have children. Determine all possible blood phenotypes.",
        descriptionAr = "أم فصيلة دمها A هجينة (I^A i) وأب فصيلة دمه B هجين (I^B i). حدد جميع فصائل الدم المحتملة لأبنائهما.",
        p1Genotype = "Ai",
        p2Genotype = "Bi",
        inheritancePatternEn = "Codominance & Multiple Alleles (I^A, I^B, i)",
        inheritancePatternAr = "أليلات متعددة وسيادة مشتركة (الأليلان I^A و I^B سائدان معاً)",
        stepByStepSolutionEn = "1. Gametes: Mother contributes I^A or i; Father contributes I^B or i.\n2. Cross Combinations: I^A I^B (Type AB), I^A i (Type A), I^B i (Type B), ii (Type O).\n3. Phenotypic Ratios: 25% Type AB, 25% Type A, 25% Type B, 25% Type O.\n4. Biological Rule: Demonstrates codominance (AB expresses both antigens concurrently).",
        stepByStepSolutionAr = "1. أمشاج الوالدين: تنتج الأم أليلات (I^A أو i)، وينتج الأب أليلات (I^B أو i).\n2. احتمالات الإخصاب: I^A I^B (فصيلة AB)، و I^A i (فصيلة A)، و I^B i (فصيلة B)، و ii (فصيلة O).\n3. النسب المظهرية: 25% لكل فصيلة من الفصائل الأربع بالتساوي.\n4. التفسير البيولوجي: يوضح هذا التزاوج ظاهرة السيادة المشتركة (Codominance) وتعدد الأليلات."
    ),
    GeneticsProblemPreset(
        id = "hemophilia",
        titleEn = "Sex-Linked Hemophilia (X-Chromosome)",
        titleAr = "نزف الدم الهيموفيليا (مرتبط بالجنس)",
        descriptionEn = "A carrier mother (X^H X^h) and a healthy father (X^H Y) have children. What is the probability for sons and daughters?",
        descriptionAr = "أم حاملة لجين الهيموفيليا (X^H X^h) وأب سليم (X^H Y). ما هي احتمالات الإصابة بين الأبناء الذكور والإناث؟",
        p1Genotype = "Hh",
        p2Genotype = "H-",
        inheritancePatternEn = "X-Linked Recessive Inheritance",
        inheritancePatternAr = "وراثة متنحية مرتبطة بالكروموسوم الجنسي X",
        stepByStepSolutionEn = "1. Chromosomal Gametes: Mother gives X^H or X^h; Father gives X^H or Y.\n2. Daughters: X^H X^H (100% healthy, 50% non-carrier, 50% carrier X^H X^h). No daughters have hemophilia.\n3. Sons: 50% X^H Y (healthy), 50% X^h Y (hemophiliac).\n4. Clinical Fact: Hemophilia affects males primarily because they lack a second X chromosome to mask recessive mutations.",
        stepByStepSolutionAr = "1. الأمشاج الجنسية: الأم تعطي (X^H أو X^h)، والأب يعطي (X^H أو Y).\n2. البنات الإناث: 100% سليمات ظاهرياً (50% نقية و 50% حاملة للمرض دون إصابة).\n3. الأبناء الذكور: 50% ذكور أصحاء (X^H Y)، و 50% ذكور مصابون بالهيموفيليا (X^h Y).\n4. حقيقة طبية: تصيب الأمراض المرتبطة بـ X الذكور أكثر لعدم وجود كروموسوم X ثانٍ يعوض الأليل المتنحي."
    )
)

@Composable
fun MendelianSolverScreen(
    viewModel: GeneLabViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val isAr = language == AppLanguage.ARABIC

    var selectedPreset by remember { mutableStateOf(PROBLEM_PRESETS[0]) }
    var customProblemText by remember { mutableStateOf("") }

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
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                glowing = true
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ElectricViolet.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Calculate, contentDescription = null, tint = ElectricViolet, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isAr) "محلل مسائل الوراثة المندلية" else "Mendelian Genetics Problem Solver",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (isAr) "حل خطوة بخطوة مع حساب الأليلات ومصفوفة الاحتمالات" else "Step-by-step allele deduction & outcome probabilities",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Scenario Preset Chips
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (isAr) "اختر سيناريو وراثي كلاسيكي:" else "Select Clinical / Classical Case:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(PROBLEM_PRESETS) { preset ->
                        val isSelected = selectedPreset.id == preset.id
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) ElectricViolet.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) ElectricViolet else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedPreset = preset }
                        ) {
                            Text(
                                text = if (isAr) preset.titleAr else preset.titleEn,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) ElectricViolet else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Selected Preset Problem Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("problem_case_card"),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ) {
                Text(
                    text = if (isAr) selectedPreset.titleAr else selectedPreset.titleEn,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = NeonCyan
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (isAr) selectedPreset.descriptionAr else selectedPreset.descriptionEn,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Biotech, contentDescription = null, tint = BioEmerald, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isAr) "نمط الوراثة: ${selectedPreset.inheritancePatternAr}"
                            else "Pattern: ${selectedPreset.inheritancePatternEn}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BioEmerald
                        )
                    }
                }
            }
        }

        // Step-by-Step Mathematical Solution Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth().testTag("solution_steps_card"),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                glowing = true
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = BioEmerald, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isAr) "خطوات الحل والحسابات الرياضية" else "Step-by-Step Resolution & Probability",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isAr) selectedPreset.stepByStepSolutionAr else selectedPreset.stepByStepSolutionEn,
                    fontSize = 13.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        viewModel.sendMessage(
                            if (isAr) "اشرح لي بالتفصيل مسألة: ${selectedPreset.titleAr}"
                            else "Explain in detail the genetics case: ${selectedPreset.titleEn}"
                        )
                        viewModel.navigateTo(AppNavDestination.AI_ASSISTANT)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricViolet,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().testTag("ask_ai_solution_button")
                ) {
                    Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isAr) "شرح المسألة بالمساعد الذكي" else "Deep Dive with AI Assistant",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Custom User Problem Input Field
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Text(
                    text = if (isAr) "أو اكتب مسألتك الخاصة هنا:" else "Or Enter Your Custom Genetics Problem:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = customProblemText,
                    onValueChange = { customProblemText = it },
                    placeholder = {
                        Text(
                            text = if (isAr) "مثال: تزاوج بين نبات بازلاء أملس البذور هجين (Rr) مع نبات مجعد (rr)..."
                            else "e.g., Cross between heterozygous round pea plant (Rr) and wrinkled (rr)...",
                            fontSize = 12.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(100.dp).testTag("custom_problem_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (customProblemText.isNotBlank()) {
                            viewModel.sendMessage(customProblemText)
                            viewModel.navigateTo(AppNavDestination.AI_ASSISTANT)
                        }
                    },
                    enabled = customProblemText.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonCyan,
                        contentColor = Color(0xFF00222B)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().testTag("solve_custom_problem_button")
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isAr) "تحليل وحل المسألة بالذكاء الاصطناعي" else "Analyze & Solve with AI", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}
