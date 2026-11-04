package com.example.data.genetics

import com.example.data.model.*
import kotlin.math.pow
import kotlin.random.Random

object GeneticsEngine {

    val DEFAULT_TRAITS = listOf(
        TraitPreset(
            id = "pea_flower",
            nameEn = "Pea Flower Color",
            nameAr = "لون زهرة البازلاء",
            dominantAllele = 'P',
            recessiveAllele = 'p',
            dominantPhenotypeEn = "Purple Flowers (Dominant)",
            dominantPhenotypeAr = "أزهار بنفسجية (سائدة)",
            recessivePhenotypeEn = "White Flowers (Recessive)",
            recessivePhenotypeAr = "أزهار بيضاء (متنحية)"
        ),
        TraitPreset(
            id = "pea_seed_shape",
            nameEn = "Pea Seed Shape",
            nameAr = "شكل بذور البازلاء",
            dominantAllele = 'R',
            recessiveAllele = 'r',
            dominantPhenotypeEn = "Round Seeds (Dominant)",
            dominantPhenotypeAr = "بذور ملساء (سائدة)",
            recessivePhenotypeEn = "Wrinkled Seeds (Recessive)",
            recessivePhenotypeAr = "بذور مجعدة (متنحية)"
        ),
        TraitPreset(
            id = "pea_seed_color",
            nameEn = "Pea Seed Color",
            nameAr = "لون بذور البازلاء",
            dominantAllele = 'Y',
            recessiveAllele = 'y',
            dominantPhenotypeEn = "Yellow Seeds (Dominant)",
            dominantPhenotypeAr = "بذور صفراء (سائدة)",
            recessivePhenotypeEn = "Green Seeds (Recessive)",
            recessivePhenotypeAr = "بذور خضراء (متنحية)"
        ),
        TraitPreset(
            id = "plant_height",
            nameEn = "Pea Plant Height",
            nameAr = "طول ساق البازلاء",
            dominantAllele = 'T',
            recessiveAllele = 't',
            dominantPhenotypeEn = "Tall Plant (Dominant)",
            dominantPhenotypeAr = "ساق طويلة (سائدة)",
            recessivePhenotypeEn = "Dwarf Plant (Recessive)",
            recessivePhenotypeAr = "ساق قصيرة (متنحية)"
        )
    )

    fun calculateMonohybrid(p1: String, p2: String, trait: TraitPreset? = null): PunnettResult {
        val cleanP1 = p1.filter { it.isLetter() }.take(2).padEnd(2, 'a')
        val cleanP2 = p2.filter { it.isLetter() }.take(2).padEnd(2, 'a')

        val g1 = listOf(cleanP1[0].toString(), cleanP1[1].toString())
        val g2 = listOf(cleanP2[0].toString(), cleanP2[1].toString())

        val grid = mutableListOf<List<String>>()
        val counts = mutableMapOf<String, Int>()

        for (a2 in g2) {
            val row = mutableListOf<String>()
            for (a1 in g1) {
                val combined = normalizeGenotype(a1 + a2)
                row.add(combined)
                counts[combined] = (counts[combined] ?: 0) + 1
            }
            grid.add(row)
        }

        val total = 4
        val percentages = counts.mapValues { (it.value.toFloat() / total) * 100f }

        // Phenotypes
        val domChar = trait?.dominantAllele ?: cleanP1.firstOrNull { it.isUpperCase() } ?: cleanP1[0]
        val domNameEn = trait?.dominantPhenotypeEn ?: "Dominant phenotype ($domChar-)"
        val recNameEn = trait?.recessivePhenotypeEn ?: "Recessive phenotype"
        val domNameAr = trait?.dominantPhenotypeAr ?: "صفة سائدة ($domChar-)"
        val recNameAr = trait?.recessivePhenotypeAr ?: "صفة متنحية"

        var domCount = 0
        var recCount = 0
        for ((gt, count) in counts) {
            if (gt.any { it == domChar.uppercaseChar() }) {
                domCount += count
            } else {
                recCount += count
            }
        }

        val phenoFreq = mapOf(domNameEn to domCount, recNameEn to recCount)
        val phenoPercent = mapOf(
            domNameEn to (domCount.toFloat() / total) * 100f,
            recNameEn to (recCount.toFloat() / total) * 100f
        )

        val explanationEn = buildString {
            append("1. Parental Genotypes: Parent 1 is '$cleanP1' and Parent 2 is '$cleanP2'.\n")
            append("2. Gamete Segregation (Mendel's 1st Law): P1 produces gametes [${g1.joinToString()}], and P2 produces gametes [${g2.joinToString()}].\n")
            append("3. Fertilization Combinations: 4 possible offspring combinations are formed in the Punnett Square.\n")
            append("4. Genotypic Ratio: ")
            append(counts.entries.joinToString(" : ") { "${it.value} ${it.key} (${percentages[it.key]?.toInt()}%)" })
            append(".\n5. Phenotypic Ratio: $domCount $domNameEn : $recCount $recNameEn (Ratio $domCount:$recCount).")
        }

        val explanationAr = buildString {
            append("1. الطرز الجينية للآباء: الأب الأول '$cleanP1' والأب الثاني '$cleanP2'.\n")
            append("2. انعزال الأمشاج (قانون مندل الأول): ينتج الأب الأول أمشاج [${g1.joinToString()}]، وينتج الأب الثاني [${g2.joinToString()}].\n")
            append("3. مصفوفة التلقيح: تتشكل 4 احتمالات للأبناء في مربع بانيت.\n")
            append("4. النسبة الجينية: ")
            append(counts.entries.joinToString(" : ") { "${it.value} ${it.key} (${percentages[it.key]?.toInt()}%)" })
            append(".\n5. النسبة المظهرية: $domCount $domNameAr مقابل $recCount $recNameAr (بنسبة $domCount : $recCount).")
        }

        return PunnettResult(
            parent1Gametes = g1,
            parent2Gametes = g2,
            grid = grid,
            genotypeFrequencies = counts,
            genotypePercentages = percentages,
            phenotypeFrequencies = phenoFreq,
            phenotypePercentages = phenoPercent,
            explanationEn = explanationEn,
            explanationAr = explanationAr
        )
    }

    fun calculateDihybrid(p1: String, p2: String): PunnettResult {
        // e.g. AaBb x AaBb
        val cleanP1 = p1.filter { it.isLetter() }.take(4).padEnd(4, 'a')
        val cleanP2 = p2.filter { it.isLetter() }.take(4).padEnd(4, 'a')

        val g1 = listOf(
            "${cleanP1[0]}${cleanP1[2]}",
            "${cleanP1[0]}${cleanP1[3]}",
            "${cleanP1[1]}${cleanP1[2]}",
            "${cleanP1[1]}${cleanP1[3]}"
        )
        val g2 = listOf(
            "${cleanP2[0]}${cleanP2[2]}",
            "${cleanP2[0]}${cleanP2[3]}",
            "${cleanP2[1]}${cleanP2[2]}",
            "${cleanP2[1]}${cleanP2[3]}"
        )

        val grid = mutableListOf<List<String>>()
        val counts = mutableMapOf<String, Int>()

        for (a2 in g2) {
            val row = mutableListOf<String>()
            for (a1 in g1) {
                val traitA = normalizeGenotype("${a1[0]}${a2[0]}")
                val traitB = normalizeGenotype("${a1[1]}${a2[1]}")
                val combined = "$traitA$traitB"
                row.add(combined)
                counts[combined] = (counts[combined] ?: 0) + 1
            }
            grid.add(row)
        }

        val total = 16
        val percentages = counts.mapValues { (it.value.toFloat() / total) * 100f }

        // Phenotype 9:3:3:1
        val domA = cleanP1[0].uppercaseChar()
        val domB = cleanP1[2].uppercaseChar()

        var domDom = 0
        var domRec = 0
        var recDom = 0
        var recRec = 0

        for ((gt, count) in counts) {
            val hasDomA = gt.substring(0, 2).any { it.isUpperCase() }
            val hasDomB = gt.substring(2, 4).any { it.isUpperCase() }

            when {
                hasDomA && hasDomB -> domDom += count
                hasDomA && !hasDomB -> domRec += count
                !hasDomA && hasDomB -> recDom += count
                else -> recRec += count
            }
        }

        val phenoFreq = mapOf(
            "Dom A / Dom B" to domDom,
            "Dom A / Rec b" to domRec,
            "Rec a / Dom B" to recDom,
            "Rec a / Rec b" to recRec
        )

        val phenoPercent = mapOf(
            "Dom A / Dom B" to (domDom.toFloat() / total) * 100f,
            "Dom A / Rec b" to (domRec.toFloat() / total) * 100f,
            "Rec a / Dom B" to (recDom.toFloat() / total) * 100f,
            "Rec a / Rec b" to (recRec.toFloat() / total) * 100f
        )

        val explanationEn = "Mendel's Law of Independent Assortment: Alleles for different traits segregate independently during gamete formation. The 16-box Punnett Square yields the classic Mendelian 9:3:3:1 phenotypic ratio ($domDom : $domRec : $recDom : $recRec)."
        val explanationAr = "قانون التوزيع الحر لمندل: تنفصل أليلات الصفات المختلفة بشكل مستقل أثناء تكوين الأمشاج. ينتج عن مربع بانيت المكون من 16 خلية النسبة المظهرية الكلاسيكية 9:3:3:1 ($domDom : $domRec : $recDom : $recRec)."

        return PunnettResult(
            parent1Gametes = g1,
            parent2Gametes = g2,
            grid = grid,
            genotypeFrequencies = counts,
            genotypePercentages = percentages,
            phenotypeFrequencies = phenoFreq,
            phenotypePercentages = phenoPercent,
            explanationEn = explanationEn,
            explanationAr = explanationAr
        )
    }

    fun runSimulation(p1: String, p2: String, sampleSize: Int, trait: TraitPreset? = null): SimulationSummary {
        val theoretical = calculateMonohybrid(p1, p2, trait)
        val g1 = theoretical.parent1Gametes
        val g2 = theoretical.parent2Gametes

        val observedGtCounts = mutableMapOf<String, Int>()
        val domChar = trait?.dominantAllele ?: p1.firstOrNull { it.isUpperCase() } ?: 'A'
        val domName = trait?.dominantPhenotypeEn ?: "Dominant ($domChar-)"
        val recName = trait?.recessivePhenotypeEn ?: "Recessive"

        var observedDom = 0
        var observedRec = 0

        for (i in 0 until sampleSize) {
            val a1 = g1[Random.nextInt(g1.size)]
            val a2 = g2[Random.nextInt(g2.size)]
            val gt = normalizeGenotype(a1 + a2)
            observedGtCounts[gt] = (observedGtCounts[gt] ?: 0) + 1

            if (gt.any { it == domChar.uppercaseChar() }) {
                observedDom++
            } else {
                observedRec++
            }
        }

        val observedGtPercentages = observedGtCounts.mapValues { (it.value.toFloat() / sampleSize) * 100f }
        val observedPhenoCounts = mapOf(domName to observedDom, recName to observedRec)
        val observedPhenoPercentages = mapOf(
            domName to (observedDom.toFloat() / sampleSize) * 100f,
            recName to (observedRec.toFloat() / sampleSize) * 100f
        )

        // Calculate Chi-Square statistic
        var chiSq = 0.0
        for ((pheno, expectedPct) in theoretical.phenotypePercentages) {
            val expectedCount = (expectedPct / 100.0) * sampleSize
            val actualCount = (observedPhenoCounts[pheno] ?: 0).toDouble()
            if (expectedCount > 0) {
                chiSq += (actualCount - expectedCount).pow(2.0) / expectedCount
            }
        }

        val pValDesc = if (chiSq < 3.841) "p > 0.05 (Consistent with Mendelian Expectations)" else "p <= 0.05 (Statistically significant deviation by chance)"
        val pValDescAr = if (chiSq < 3.841) "القيمة الاحتمالية p > 0.05 (تتوافق نتائج العينة مع التوقعات المندلية)" else "القيمة الاحتمالية p <= 0.05 (انحراف إحصائي ملحوظ بسبب العشوائية)"

        val analysisEn = "In a sample of N = $sampleSize offspring, random gamete union produced $observedDom dominant and $observedRec recessive phenotypes. With Chi-Square = ${String.format("%.2f", chiSq)}, $pValDesc. Notice how larger sample sizes converge much closer to theoretical ratios (Law of Large Numbers)."
        val analysisAr = "في عينة من $sampleSize نسلاً، أنتج الاتحاد العشوائي للأمشاج $observedDom مظهراً سائداً و $observedRec مظهراً متنحياً. بقيمة كاي تربيع = ${String.format("%.2f", chiSq)}، $pValDescAr. لاحظ كيف تقترب العينات الكبيرة بشكل وثيق من النسب النظرية (قانون الأعداد الكبيرة)."

        return SimulationSummary(
            totalOffspring = sampleSize,
            observedGenotypes = observedGtCounts,
            observedGenotypePercentages = observedGtPercentages,
            expectedGenotypePercentages = theoretical.genotypePercentages,
            observedPhenotypes = observedPhenoCounts,
            observedPhenotypePercentages = observedPhenoPercentages,
            expectedPhenotypePercentages = theoretical.phenotypePercentages,
            chiSquareValue = chiSq.toFloat(),
            pValueApprox = pValDesc,
            analysisEn = analysisEn,
            analysisAr = analysisAr
        )
    }

    private fun normalizeGenotype(raw: String): String {
        return raw.toCharArray().sortedWith(Comparator { a, b ->
            if (a.equals(b, ignoreCase = true)) {
                if (a.isUpperCase()) -1 else 1
            } else {
                a.compareTo(b)
            }
        }).joinToString("")
    }

    data class DnaAnalysisResult(
        val originalDna: String,
        val complementaryStrand: String,
        val mrnaStrand: String,
        val codons: List<Pair<String, AminoAcid?>>,
        val isValid: Boolean,
        val errorMessage: String? = null
    )

    fun analyzeDnaSequence(dnaInput: String): DnaAnalysisResult {
        val clean = dnaInput.uppercase().filter { !it.isWhitespace() }
        if (clean.isEmpty()) {
            return DnaAnalysisResult("", "", "", emptyList(), false, "Please enter a DNA sequence")
        }

        val invalidChars = clean.filter { it !in listOf('A', 'T', 'C', 'G') }
        if (invalidChars.isNotEmpty()) {
            return DnaAnalysisResult(
                clean, "", "", emptyList(), false,
                "Invalid bases detected: ${invalidChars.toSet().joinToString()}. Only A, T, C, G are allowed in DNA."
            )
        }

        // Complementary strand (3' to 5')
        val comp = clean.map { Nucleotide.getComplement(it, isRna = false) }.joinToString("")
        // mRNA transcript (coding strand replaces Thymine T with Uracil U)
        val mrna = clean.map { if (it == 'T') 'U' else it }.joinToString("")

        val codons = GeneticCode.translateMrna(mrna)

        return DnaAnalysisResult(
            originalDna = clean,
            complementaryStrand = comp,
            mrnaStrand = mrna,
            codons = codons,
            isValid = true
        )
    }
}
