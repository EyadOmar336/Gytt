package com.example.data.model

enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    ARABIC("ar", "العربية", "🇸🇦"),
    ENGLISH("en", "English", "🇬🇧")
}

// Nucleotide base definitions
enum class Nucleotide(val symbol: Char, val fullNameEn: String, val fullNameAr: String, val isRnaOnly: Boolean = false) {
    ADENINE('A', "Adenine", "أدينين"),
    THYMINE('T', "Thymine", "ثايمين"),
    CYTOSINE('C', "Cytosine", "سيتوسين"),
    GUANINE('G', "Guanine", "جوانين"),
    URACIL('U', "Uracil", "يوراسيل", isRnaOnly = true);

    companion object {
        fun fromChar(c: Char): Nucleotide? = entries.firstOrNull { it.symbol == c.uppercaseChar() }
        
        fun getComplement(c: Char, isRna: Boolean = false): Char {
            return when (c.uppercaseChar()) {
                'A' -> if (isRna) 'U' else 'T'
                'T' -> 'A'
                'U' -> 'A'
                'C' -> 'G'
                'G' -> 'C'
                else -> 'N'
            }
        }
    }
}

// Genetic Codon and Amino Acid Mapping
data class AminoAcid(
    val nameEn: String,
    val nameAr: String,
    val shortCode: String,
    val singleLetter: Char,
    val colorHex: Long
)

object GeneticCode {
    val AMINO_ACID_MAP: Map<String, AminoAcid> = mapOf(
        "AUG" to AminoAcid("Methionine (START)", "ميثيونين (بدء)", "Met", 'M', 0xFF10B981),
        "UUU" to AminoAcid("Phenylalanine", "فينيل ألانين", "Phe", 'F', 0xFF3B82F6),
        "UUC" to AminoAcid("Phenylalanine", "فينيل ألانين", "Phe", 'F', 0xFF3B82F6),
        "UUA" to AminoAcid("Leucine", "ليوسين", "Leu", 'L', 0xFF6366F1),
        "UUG" to AminoAcid("Leucine", "ليوسين", "Leu", 'L', 0xFF6366F1),
        "CUU" to AminoAcid("Leucine", "ليوسين", "Leu", 'L', 0xFF6366F1),
        "CUC" to AminoAcid("Leucine", "ليوسين", "Leu", 'L', 0xFF6366F1),
        "CUA" to AminoAcid("Leucine", "ليوسين", "Leu", 'L', 0xFF6366F1),
        "CUG" to AminoAcid("Leucine", "ليوسين", "Leu", 'L', 0xFF6366F1),
        "AUU" to AminoAcid("Isoleucine", "آيزوليوسين", "Ile", 'I', 0xFF8B5CF6),
        "AUC" to AminoAcid("Isoleucine", "آيزوليوسين", "Ile", 'I', 0xFF8B5CF6),
        "AUA" to AminoAcid("Isoleucine", "آيزوليوسين", "Ile", 'I', 0xFF8B5CF6),
        "GUU" to AminoAcid("Valine", "فالين", "Val", 'V', 0xFFEC4899),
        "GUC" to AminoAcid("Valine", "فالين", "Val", 'V', 0xFFEC4899),
        "GUA" to AminoAcid("Valine", "فالين", "Val", 'V', 0xFFEC4899),
        "GUG" to AminoAcid("Valine", "فالين", "Val", 'V', 0xFFEC4899),
        "UCU" to AminoAcid("Serine", "سيرين", "Ser", 'S', 0xFF14B8A6),
        "UCC" to AminoAcid("Serine", "سيرين", "Ser", 'S', 0xFF14B8A6),
        "UCA" to AminoAcid("Serine", "سيرين", "Ser", 'S', 0xFF14B8A6),
        "UCG" to AminoAcid("Serine", "سيرين", "Ser", 'S', 0xFF14B8A6),
        "CCU" to AminoAcid("Proline", "برولين", "Pro", 'P', 0xFFF59E0B),
        "CCC" to AminoAcid("Proline", "برولين", "Pro", 'P', 0xFFF59E0B),
        "CCA" to AminoAcid("Proline", "برولين", "Pro", 'P', 0xFFF59E0B),
        "CCG" to AminoAcid("Proline", "برولين", "Pro", 'P', 0xFFF59E0B),
        "ACU" to AminoAcid("Threonine", "ثريونين", "Thr", 'T', 0xFF06B6D4),
        "ACC" to AminoAcid("Threonine", "ثريونين", "Thr", 'T', 0xFF06B6D4),
        "ACA" to AminoAcid("Threonine", "ثريونين", "Thr", 'T', 0xFF06B6D4),
        "ACG" to AminoAcid("Threonine", "ثريونين", "Thr", 'T', 0xFF06B6D4),
        "GCU" to AminoAcid("Alanine", "ألانين", "Ala", 'A', 0xFF84CC16),
        "GCC" to AminoAcid("Alanine", "ألانين", "Ala", 'A', 0xFF84CC16),
        "GCA" to AminoAcid("Alanine", "ألانين", "Ala", 'A', 0xFF84CC16),
        "GCG" to AminoAcid("Alanine", "ألانين", "Ala", 'A', 0xFF84CC16),
        "UAU" to AminoAcid("Tyrosine", "تيروزين", "Tyr", 'Y', 0xFFEAB308),
        "UAC" to AminoAcid("Tyrosine", "تيروزين", "Tyr", 'Y', 0xFFEAB308),
        "UAA" to AminoAcid("STOP (Ochre)", "توقف (Stop)", "STP", '*', 0xFFEF4444),
        "UAG" to AminoAcid("STOP (Amber)", "توقف (Stop)", "STP", '*', 0xFFEF4444),
        "UGA" to AminoAcid("STOP (Opal)", "توقف (Stop)", "STP", '*', 0xFFEF4444),
        "CAU" to AminoAcid("Histidine", "هيستيدين", "His", 'H', 0xFFF97316),
        "CAC" to AminoAcid("Histidine", "هيستيدين", "His", 'H', 0xFFF97316),
        "CAA" to AminoAcid("Glutamine", "جلوتامين", "Gln", 'Q', 0xFF0284C7),
        "CAG" to AminoAcid("Glutamine", "جلوتامين", "Gln", 'Q', 0xFF0284C7),
        "AAU" to AminoAcid("Asparagine", "أسباراجين", "Asn", 'N', 0xFFA855F7),
        "AAC" to AminoAcid("Asparagine", "أسباراجين", "Asn", 'N', 0xFFA855F7),
        "AAA" to AminoAcid("Lysine", "لايسين", "Lys", 'K', 0xFF3B82F6),
        "AAG" to AminoAcid("Lysine", "لايسين", "Lys", 'K', 0xFF3B82F6),
        "GAU" to AminoAcid("Aspartate", "حمض الأسبارتيك", "Asp", 'D', 0xFFD946EF),
        "GAC" to AminoAcid("Aspartate", "حمض الأسبارتيك", "Asp", 'D', 0xFFD946EF),
        "GAA" to AminoAcid("Glutamate", "حمض الجلوتاميك", "Glu", 'E', 0xFF10B981),
        "GAG" to AminoAcid("Glutamate", "حمض الجلوتاميك", "Glu", 'E', 0xFF10B981),
        "UGU" to AminoAcid("Cysteine", "سيستين", "Cys", 'C', 0xFF06B6D4),
        "UGC" to AminoAcid("Cysteine", "سيستين", "Cys", 'C', 0xFF06B6D4),
        "UGG" to AminoAcid("Tryptophan", "تريبتوفان", "Trp", 'W', 0xFF6366F1),
        "CGU" to AminoAcid("Arginine", "أرجينين", "Arg", 'R', 0xFF3B82F6),
        "CGC" to AminoAcid("Arginine", "أرجينين", "Arg", 'R', 0xFF3B82F6),
        "CGA" to AminoAcid("Arginine", "أرجينين", "Arg", 'R', 0xFF3B82F6),
        "CGG" to AminoAcid("Arginine", "أرجينين", "Arg", 'R', 0xFF3B82F6),
        "AGU" to AminoAcid("Serine", "سيرين", "Ser", 'S', 0xFF14B8A6),
        "AGC" to AminoAcid("Serine", "سيرين", "Ser", 'S', 0xFF14B8A6),
        "AGA" to AminoAcid("Arginine", "أرجينين", "Arg", 'R', 0xFF3B82F6),
        "AGG" to AminoAcid("Arginine", "أرجينين", "Arg", 'R', 0xFF3B82F6),
        "GGU" to AminoAcid("Glycine", "جلايسين", "Gly", 'G', 0xFF22C55E),
        "GGC" to AminoAcid("Glycine", "جلايسين", "Gly", 'G', 0xFF22C55E),
        "GGA" to AminoAcid("Glycine", "جلايسين", "Gly", 'G', 0xFF22C55E),
        "GGG" to AminoAcid("Glycine", "جلايسين", "Gly", 'G', 0xFF22C55E)
    )

    fun translateMrna(mrna: String): List<Pair<String, AminoAcid?>> {
        val clean = mrna.uppercase().replace(" ", "").replace("\n", "")
        val result = mutableListOf<Pair<String, AminoAcid?>>()
        var i = 0
        while (i + 2 < clean.length) {
            val codon = clean.substring(i, i + 3)
            result.add(Pair(codon, AMINO_ACID_MAP[codon]))
            i += 3
        }
        return result
    }
}

// Punnett Square Data Models
data class TraitPreset(
    val id: String,
    val nameEn: String,
    val nameAr: String,
    val dominantAllele: Char,
    val recessiveAllele: Char,
    val dominantPhenotypeEn: String,
    val dominantPhenotypeAr: String,
    val recessivePhenotypeEn: String,
    val recessivePhenotypeAr: String
)

data class PunnettResult(
    val parent1Gametes: List<String>,
    val parent2Gametes: List<String>,
    val grid: List<List<String>>, // matrix of resulting genotypes
    val genotypeFrequencies: Map<String, Int>,
    val genotypePercentages: Map<String, Float>,
    val phenotypeFrequencies: Map<String, Int>,
    val phenotypePercentages: Map<String, Float>,
    val explanationEn: String,
    val explanationAr: String
)

// Simulation Data
data class SimulationSummary(
    val totalOffspring: Int,
    val observedGenotypes: Map<String, Int>,
    val observedGenotypePercentages: Map<String, Float>,
    val expectedGenotypePercentages: Map<String, Float>,
    val observedPhenotypes: Map<String, Int>,
    val observedPhenotypePercentages: Map<String, Float>,
    val expectedPhenotypePercentages: Map<String, Float>,
    val chiSquareValue: Float,
    val pValueApprox: String,
    val analysisEn: String,
    val analysisAr: String
)

// Mutation Data
enum class MutationType(val labelEn: String, val labelAr: String, val descEn: String, val descAr: String) {
    SILENT_SUBSTITUTION(
        "Silent Substitution",
        "طفرة استبدال صامتة",
        "A single nucleotide is changed, but it codes for the same amino acid due to code degeneracy.",
        "تغير نيوكليوتيد واحد ولكن ينتج نفس الحمض الأميني بسبب التكرار في الشفرة الوراثية."
    ),
    MISSENSE_SUBSTITUTION(
        "Missense Substitution",
        "طفرة استبدال خاطئة (مؤثرة)",
        "A nucleotide change alters a codon so that it codes for a different amino acid.",
        "تغير نيوكليوتيد يغير الشفرة لإنتاج حمض أميني مختلف (مثل فقر الدم المنجلي)."
    ),
    NONSENSE_SUBSTITUTION(
        "Nonsense Substitution",
        "طفرة استبدال غير مجدية (توقف مبكر)",
        "A base change transforms a normal amino acid codon into a premature STOP codon.",
        "تغير نيوكليوتيد يحول شفرة حمض أميني إلى شفرة توقف مبكرة، مما يقصر البروتين."
    ),
    INSERTION_FRAMESHIFT(
        "Insertion (Frameshift)",
        "طفرة إزاحة بالإدخال",
        "Addition of one or more base pairs shifts the reading frame for all subsequent codons.",
        "إضافة نيوكليوتيد أو أكثر مما يؤدي إلى إزاحة إطار القراءة لكل الشفرات اللاحقة."
    ),
    DELETION_FRAMESHIFT(
        "Deletion (Frameshift)",
        "طفرة إزاحة بالحذف",
        "Loss of one or more base pairs shifts the genetic reading frame, altering the protein.",
        "حذف نيوكليوتيد أو أكثر مما يزيح إطار القراءة بالكامل ويغير البروتين الناتج."
    )
}

data class MutationExample(
    val id: String,
    val type: MutationType,
    val titleEn: String,
    val titleAr: String,
    val originalDna: String,
    val mutatedDna: String,
    val mutatedPosition: Int, // 0-indexed
    val biologicalImpactEn: String,
    val biologicalImpactAr: String
)

// Glossary Term
data class GlossaryTerm(
    val id: String,
    val termEn: String,
    val termAr: String,
    val category: String, // "Molecular", "Classical", "Cellular", "Biotech"
    val definitionEn: String,
    val definitionAr: String,
    val formulaOrSymbol: String? = null
)

// Lesson Models
data class Lesson(
    val id: String,
    val track: String, // "Beginner", "Intermediate", "Advanced"
    val trackAr: String,
    val titleEn: String,
    val titleAr: String,
    val durationMin: Int,
    val iconName: String,
    val summaryEn: String,
    val summaryAr: String,
    val sections: List<LessonSection>,
    val quickQuiz: List<QuizQuestion>
)

data class LessonSection(
    val headingEn: String,
    val headingAr: String,
    val contentEn: String,
    val contentAr: String,
    val keyTakeawayEn: String? = null,
    val keyTakeawayAr: String? = null
)

data class QuizQuestion(
    val id: String,
    val questionEn: String,
    val questionAr: String,
    val optionsEn: List<String>,
    val optionsAr: List<String>,
    val correctIndex: Int,
    val explanationEn: String,
    val explanationAr: String,
    val topic: String
)
