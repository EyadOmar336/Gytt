package com.example

import com.example.data.genetics.GeneticsEngine
import com.example.data.model.Nucleotide
import org.junit.Assert.*
import org.junit.Test

class GeneticsEngineTest {

    @Test
    fun testMonohybridHeterozygousCross() {
        val result = GeneticsEngine.calculateMonohybrid("Aa", "Aa")
        assertEquals(2, result.parent1Gametes.size)
        assertEquals(2, result.parent2Gametes.size)
        assertEquals(2, result.grid.size)
        assertEquals(2, result.grid[0].size)

        // 1 AA : 2 Aa : 1 aa
        assertEquals(1, result.genotypeFrequencies["AA"])
        assertEquals(2, result.genotypeFrequencies["Aa"])
        assertEquals(1, result.genotypeFrequencies["aa"])

        // Phenotypes: 3 dominant : 1 recessive (75% / 25%)
        val totalDominant = (result.phenotypePercentages.values.maxOrNull() ?: 0f)
        assertEquals(75f, totalDominant, 0.01f)
    }

    @Test
    fun testDihybridIndependentAssortment() {
        val result = GeneticsEngine.calculateDihybrid("AaBb", "AaBb")
        assertEquals(4, result.parent1Gametes.size)
        assertEquals(4, result.parent2Gametes.size)
        assertEquals(4, result.grid.size)
        assertEquals(4, result.grid[0].size)

        // 9:3:3:1 phenotypic ratio
        assertEquals(9, result.phenotypeFrequencies["Dom A / Dom B"])
        assertEquals(3, result.phenotypeFrequencies["Dom A / Rec b"])
        assertEquals(3, result.phenotypeFrequencies["Rec a / Dom B"])
        assertEquals(1, result.phenotypeFrequencies["Rec a / Rec b"])
    }

    @Test
    fun testDnaAnalysisAndTranslation() {
        // AUG = Met, CGA = Arg, UAC = Tyr, GCU = Ala, UGA = Stop
        val dna = "ATGCGATACGCTTGA"
        val analysis = GeneticsEngine.analyzeDnaSequence(dna)

        assertTrue(analysis.isValid)
        assertEquals("TACGCTATGCGAACT", analysis.complementaryStrand)
        assertEquals("AUGCGAUACGCUUGA", analysis.mrnaStrand)
        assertEquals(5, analysis.codons.size)

        // 1st codon is start Met
        assertEquals("AUG", analysis.codons[0].first)
        assertEquals("Met", analysis.codons[0].second?.shortCode)

        // Last codon is stop
        assertEquals("UGA", analysis.codons[4].first)
        assertEquals("STP", analysis.codons[4].second?.shortCode)
    }

    @Test
    fun testInvalidDnaValidation() {
        val invalidDna = "ATGCXYZ"
        val analysis = GeneticsEngine.analyzeDnaSequence(invalidDna)
        assertFalse(analysis.isValid)
        assertNotNull(analysis.errorMessage)
    }

    @Test
    fun testBreedingSimulation() {
        val summary = GeneticsEngine.runSimulation("Aa", "Aa", 100)
        assertEquals(100, summary.totalOffspring)
        val totalObserved = summary.observedGenotypes.values.sum()
        assertEquals(100, totalObserved)
        assertTrue(summary.chiSquareValue >= 0f)
    }
}
