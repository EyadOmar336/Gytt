// GeneLab AI — Responsive Web Application Engine
let currentLang = 'ar';
let currentTheme = 'dark';
let punnettMode = 'mono';
let activePeaTrait = 'flower';

// Genetic Code Table
const GENETIC_CODE = {
    "AUG": { nameEn: "Methionine (START)", nameAr: "ميثيونين (بدء)", code: "Met", color: "#10b981" },
    "UUU": { nameEn: "Phenylalanine", nameAr: "فينيل ألانين", code: "Phe", color: "#3b82f6" },
    "UUC": { nameEn: "Phenylalanine", nameAr: "فينيل ألانين", code: "Phe", color: "#3b82f6" },
    "UUA": { nameEn: "Leucine", nameAr: "ليوسين", code: "Leu", color: "#6366f1" },
    "UUG": { nameEn: "Leucine", nameAr: "ليوسين", code: "Leu", color: "#6366f1" },
    "CUU": { nameEn: "Leucine", nameAr: "ليوسين", code: "Leu", color: "#6366f1" },
    "CUC": { nameEn: "Leucine", nameAr: "ليوسين", code: "Leu", color: "#6366f1" },
    "AUU": { nameEn: "Isoleucine", nameAr: "آيزوليوسين", code: "Ile", color: "#8b5cf6" },
    "AUC": { nameEn: "Isoleucine", nameAr: "آيزوليوسين", code: "Ile", color: "#8b5cf6" },
    "GUU": { nameEn: "Valine", nameAr: "فالين", code: "Val", color: "#ec4899" },
    "GUC": { nameEn: "Valine", nameAr: "فالين", code: "Val", color: "#ec4899" },
    "UCU": { nameEn: "Serine", nameAr: "سيرين", code: "Ser", color: "#14b8a6" },
    "CCU": { nameEn: "Proline", nameAr: "برولين", code: "Pro", color: "#f59e0b" },
    "ACU": { nameEn: "Threonine", nameAr: "ثريونين", code: "Thr", code: "Thr", color: "#06b6d4" },
    "GCU": { nameEn: "Alanine", nameAr: "ألانين", code: "Ala", color: "#84cc16" },
    "GCC": { nameEn: "Alanine", nameAr: "ألانين", code: "Ala", color: "#84cc16" },
    "UAU": { nameEn: "Tyrosine", nameAr: "تيروزين", code: "Tyr", color: "#eab308" },
    "UAC": { nameEn: "Tyrosine", nameAr: "تيروزين", code: "Tyr", color: "#eab308" },
    "UAA": { nameEn: "STOP (Ochre)", nameAr: "توقف (Stop)", code: "STP", color: "#ef4444" },
    "UAG": { nameEn: "STOP (Amber)", nameAr: "توقف (Stop)", code: "STP", color: "#ef4444" },
    "UGA": { nameEn: "STOP (Opal)", nameAr: "توقف (Stop)", code: "STP", color: "#ef4444" },
    "CAU": { nameEn: "Histidine", nameAr: "هيستيدين", code: "His", color: "#f97316" },
    "CAA": { nameEn: "Glutamine", nameAr: "جلوتامين", code: "Gln", color: "#0284c7" },
    "AAU": { nameEn: "Asparagine", nameAr: "أسباراجين", code: "Asn", color: "#a855f7" },
    "AAA": { nameEn: "Lysine", nameAr: "لايسين", code: "Lys", color: "#3b82f6" },
    "AAG": { nameEn: "Lysine", nameAr: "لايسين", code: "Lys", color: "#3b82f6" },
    "GAU": { nameEn: "Aspartate", nameAr: "حمض الأسبارتيك", code: "Asp", color: "#d946ef" },
    "GAA": { nameEn: "Glutamate", nameAr: "حمض الجلوتاميك", code: "Glu", color: "#10b981" },
    "GAG": { nameEn: "Glutamate", nameAr: "حمض الجلوتاميك", code: "Glu", color: "#10b981" },
    "UGU": { nameEn: "Cysteine", nameAr: "سيستين", code: "Cys", color: "#06b6d4" },
    "UGG": { nameEn: "Tryptophan", nameAr: "تريبتوفان", code: "Trp", color: "#6366f1" },
    "CGU": { nameEn: "Arginine", nameAr: "أرجينين", code: "Arg", color: "#3b82f6" },
    "GGU": { nameEn: "Glycine", nameAr: "جلايسين", code: "Gly", color: "#22c55e" }
};

// Classical Scenarios
const SCENARIOS = [
    {
        id: "cystic_fibrosis",
        titleAr: "التليف الكيسي (صفة متنحية)",
        titleEn: "Cystic Fibrosis Carrier Test",
        patternAr: "وراثة جسمية متنحية",
        patternEn: "Autosomal Recessive",
        descAr: "أبوان كلاهما حامل لجين التليف الكيسي المتنحي دون ظهور أعراض (Cc). ما احتمالية ولادة طفل مصاب بالمرض؟",
        descEn: "Two carrier parents (Cc) plan to have a child. What is the chance their child will have Cystic Fibrosis?",
        solutionAr: "1. الطراز الجيني للأبوين: Cc × Cc.\n2. الأمشاج: C و c من كل أب.\n3. النسبة الجينية: 25% CC، و 50% Cc، و 25% cc.\n4. النتيجة السريرية: 25% احتمال إصابة الطفل بالمرض (cc)، و 75% احتمال سلامته ظاهرياً.",
        solutionEn: "1. Parental genotypes: Cc x Cc.\n2. Gametes: C and c from each parent.\n3. Genotypic ratio: 25% CC, 50% Cc, 25% cc.\n4. Clinical result: 25% probability the child inherits the disease (cc)."
    },
    {
        id: "blood_groups",
        titleAr: "فصائل الدم ABO والسيادة المشتركة",
        titleEn: "ABO Blood Group Codominance",
        patternAr: "سيادة مشتركة وأليلات متعددة",
        patternEn: "Codominance & Multiple Alleles",
        descAr: "أم فصيلة دمها A هجينة (I^A i) وأب فصيلة دمه B هجين (I^B i). حدد جميع فصائل الدم المحتملة للأبناء.",
        descEn: "Mother has heterozygous Type A (I^A i) and Father has heterozygous Type B (I^B i). Determine offspring blood types.",
        solutionAr: "1. التزاوج: I^A i × I^B i.\n2. التراكيب المحتملة: I^A I^B (فصيلة AB)، و I^A i (فصيلة A)، و I^B i (فصيلة B)، و ii (فصيلة O).\n3. النسب المظهرية: 25% لكل فصيلة من الفصائل الأربع بالتساوي.",
        solutionEn: "1. Cross: I^A i x I^B i.\n2. Genotypes: I^A I^B (AB), I^A i (A), I^B i (B), ii (O).\n3. Ratios: Equal 25% for all four blood groups."
    }
];

// Mutations
const MUTATIONS = [
    {
        id: "sickle",
        titleAr: "طفرة فقر الدم المنجلي (GAG ➔ GTG)",
        titleEn: "Sickle Cell Missense (GAG ➔ GTG)",
        typeAr: "طفرة استبدال مؤثرة (Missense)",
        typeEn: "Missense Substitution",
        pos: 19,
        originalDna: "ATGGTGCACCTGACTCCTGAGGAG",
        mutatedDna: "ATGGTGCACCTGACTCCTGTGGAG",
        descAr: "في الكودون السادس لسلسلة بيتا-جلوبين، يتغير كودون حمض الجلوتاميك GAG إلى فالين GTG كاره للماء، مما يؤدي لتشوه كريات الدم الحمراء إلى شكل منجلي.",
        descEn: "At codon 6 of Beta-globin, GAG (Glutamate) mutates to GTG (Valine), causing hemoglobin HbS to polymerize into sickle-shaped cells."
    },
    {
        id: "silent",
        titleAr: "طفرة صامتة (GAA ➔ GAG)",
        titleEn: "Silent Mutation (GAA ➔ GAG)",
        typeAr: "طفرة صامتة (Silent)",
        typeEn: "Silent Substitution",
        pos: 5,
        originalDna: "ATGGAACTG",
        mutatedDna: "ATGGAGCTG",
        descAr: "يتغير النيوكليوتيد ولكن كلا الكودونين يشفران نفس الحمض الأميني (حمض الجلوتاميك)، فلا يتغير البروتين نهائياً.",
        descEn: "The nucleotide changes but both GAA and GAG code for Glutamic Acid. The resulting protein is identical."
    }
];

// Quiz Bank
const QUIZ_BANK = [
    {
        qAr: "ما هي القاعدة النيتروجينية في الـ RNA التي تحل محل الثايمين؟",
        qEn: "Which base in RNA replaces Thymine found in DNA?",
        optsAr: ["يوراسيل (Uracil)", "أدينين", "جوانين", "سيتوسين"],
        optsEn: ["Uracil", "Adenine", "Guanine", "Cytosine"],
        correct: 0,
        expAr: "يحتوي الـ RNA على اليوراسيل (U) بدلاً من الثايمين (T).",
        expEn: "RNA contains Uracil (U) instead of Thymine (T)."
    },
    {
        qAr: "ما النسبة المظهرية المتوقعة في تزاوج أحادي هجين Aa × Aa؟",
        qEn: "What is the expected phenotypic ratio in an Aa x Aa cross?",
        optsAr: ["3 سائد : 1 متنحٍ (3:1)", "9:3:3:1", "1:1", "1:2:1"],
        optsEn: ["3 Dominant : 1 Recessive (3:1)", "9:3:3:1", "1:1", "1:2:1"],
        correct: 0,
        expAr: "تزاوج Aa × Aa ينتج 75% ذو مظهر سائد و 25% ذو مظهر متنحٍ (بنسبة 3:1).",
        expEn: "Aa x Aa yields 75% dominant and 25% recessive phenotypes (3:1 ratio)."
    },
    {
        qAr: "كم عدد الروابط الهيدروجينية بين الجوانين (G) والسيتوسين (C)؟",
        qEn: "How many hydrogen bonds form between Guanine (G) and Cytosine (C)?",
        optsAr: ["3 روابط هيدروجينية", "رابطتان", "رابطة واحدة", "4 روابط"],
        optsEn: ["3 Hydrogen bonds", "2 bonds", "1 bond", "4 bonds"],
        correct: 0,
        expAr: "يرتبط الجوانين بالسيتوسين بثلاث روابط هيدروجينية قوية.",
        expEn: "Guanine pairs with Cytosine with three strong hydrogen bonds."
    }
];

let quizCurrentIdx = 0;
let quizScore = 0;
let quizSelectedOpt = null;
let quizIsChecked = false;

// DOM Initialization
document.addEventListener("DOMContentLoaded", () => {
    initDnaCanvas("heroDnaCanvas");
    initDnaCanvas("interactiveDnaCanvas");
    calculatePunnettWeb();
    analyzeDnaWeb();
    renderScenarios();
    renderMutations();
    runBreedingSimulationWeb();
    initLearningCenter();
    startQuizWeb('All');

    // Language Toggle Listener
    document.getElementById("langToggle").addEventListener("click", toggleLanguage);
    document.getElementById("mobileLangToggle").addEventListener("click", toggleLanguage);

    // Theme Toggle Listener
    document.getElementById("themeToggle").addEventListener("click", toggleTheme);
    document.getElementById("mobileThemeToggle").addEventListener("click", toggleTheme);

    // Setup nav listeners
    document.querySelectorAll(".nav-item, .nav-tab").forEach(btn => {
        btn.addEventListener("click", () => {
            const tab = btn.getAttribute("data-tab");
            if (tab) switchTab(tab);
        });
    });
});

// Tab Switcher
function switchTab(tabId) {
    document.querySelectorAll(".tab-pane").forEach(p => p.classList.remove("active"));
    const target = document.getElementById("tab-" + tabId);
    if (target) target.classList.add("active");

    document.querySelectorAll(".nav-item, .nav-tab").forEach(btn => {
        if (btn.getAttribute("data-tab") === tabId) {
            btn.classList.add("active");
        } else {
            btn.classList.remove("active");
        }
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Language Toggle
function toggleLanguage() {
    currentLang = currentLang === 'ar' ? 'en' : 'ar';
    document.documentElement.lang = currentLang;
    document.documentElement.dir = currentLang === 'ar' ? 'rtl' : 'ltr';

    document.getElementById("langFlag").textContent = currentLang === 'ar' ? '🇸🇦' : '🇬🇧';
    document.getElementById("langText").textContent = currentLang === 'ar' ? 'English' : 'العربية';
    document.getElementById("mobileLangToggle").textContent = currentLang === 'ar' ? 'EN' : 'عربي';

    calculatePunnettWeb();
    analyzeDnaWeb();
    renderScenarios();
    renderMutations();
}

// Theme Toggle
function toggleTheme() {
    currentTheme = currentTheme === 'dark' ? 'light' : 'dark';
    document.body.className = currentTheme === 'dark' ? 'dark-theme' : 'light-theme';
    const icon = currentTheme === 'dark' ? '☀️' : '🌙';
    document.getElementById("themeIcon").textContent = icon;
    document.getElementById("mobileThemeToggle").textContent = icon;
}

// Punnett Square Logic
function setPunnettMode(mode) {
    punnettMode = mode;
    document.getElementById("btnMonoMode").classList.toggle("active", mode === 'mono');
    document.getElementById("btnDiMode").classList.toggle("active", mode === 'di');
    document.getElementById("monoTraitRow").style.display = mode === 'mono' ? 'flex' : 'none';

    if (mode === 'mono') {
        document.getElementById("p1Input").value = "Aa";
        document.getElementById("p2Input").value = "Aa";
    } else {
        document.getElementById("p1Input").value = "AaBb";
        document.getElementById("p2Input").value = "AaBb";
    }
    calculatePunnettWeb();
}

function setPeaTrait(trait) {
    activePeaTrait = trait;
    const alleles = {
        'flower': 'Pp',
        'seed_shape': 'Rr',
        'seed_color': 'Yy',
        'height': 'Tt'
    };
    const al = alleles[trait] || 'Aa';
    document.getElementById("p1Input").value = al;
    document.getElementById("p2Input").value = al;
    calculatePunnettWeb();
}

function calculatePunnettWeb() {
    const p1 = document.getElementById("p1Input").value.trim() || "Aa";
    const p2 = document.getElementById("p2Input").value.trim() || "Aa";

    if (punnettMode === 'mono') {
        const g1 = [p1[0] || 'A', p1[1] || 'a'];
        const g2 = [p2[0] || 'A', p2[1] || 'a'];

        let container = document.getElementById("punnettGridContainer");
        container.innerHTML = `
            <div class="matrix-row">
                <div class="cell" style="opacity:0.4">P1↘P2</div>
                <div class="cell header-p1">${g1[0]}</div>
                <div class="cell header-p1">${g1[1]}</div>
            </div>
            <div class="matrix-row">
                <div class="cell header-p2">${g2[0]}</div>
                <div class="cell ${isDom(g1[0]+g2[0])?'offspring-dom':'offspring-rec'}">${normGt(g1[0]+g2[0])}</div>
                <div class="cell ${isDom(g1[1]+g2[0])?'offspring-dom':'offspring-rec'}">${normGt(g1[1]+g2[0])}</div>
            </div>
            <div class="matrix-row">
                <div class="cell header-p2">${g2[1]}</div>
                <div class="cell ${isDom(g1[0]+g2[1])?'offspring-dom':'offspring-rec'}">${normGt(g1[0]+g2[1])}</div>
                <div class="cell ${isDom(g1[1]+g2[1])?'offspring-dom':'offspring-rec'}">${normGt(g1[1]+g2[1])}</div>
            </div>
        `;

        // Genotypes & Phenotypes
        const all = [normGt(g1[0]+g2[0]), normGt(g1[1]+g2[0]), normGt(g1[0]+g2[1]), normGt(g1[1]+g2[1])];
        const counts = {};
        all.forEach(g => counts[g] = (counts[g] || 0) + 1);

        let gBars = document.getElementById("genotypeBars");
        gBars.innerHTML = Object.entries(counts).map(([gt, count]) => `
            <div class="bar-item">
                <div class="bar-labels"><span>${gt}</span><span>${(count/4)*100}% (${count}/4)</span></div>
                <div class="progress-track"><div class="progress-fill" style="width:${(count/4)*100}%"></div></div>
            </div>
        `).join('');

        let domCount = all.filter(isDom).length;
        let recCount = 4 - domCount;
        let pBars = document.getElementById("phenotypeBars");
        pBars.innerHTML = `
            <div class="bar-item">
                <div class="bar-labels"><span>${currentLang==='ar'?'صفة سائدة (Dominant)':'Dominant Phenotype'}</span><span>${(domCount/4)*100}%</span></div>
                <div class="progress-track"><div class="progress-fill" style="width:${(domCount/4)*100}%;background:#10b981"></div></div>
            </div>
            <div class="bar-item">
                <div class="bar-labels"><span>${currentLang==='ar'?'صفة متنحية (Recessive)':'Recessive Phenotype'}</span><span>${(recCount/4)*100}%</span></div>
                <div class="progress-track"><div class="progress-fill" style="width:${(recCount/4)*100}%;background:#ef4444"></div></div>
            </div>
        `;

        document.getElementById("punnettExplanation").textContent = currentLang === 'ar'
            ? `1. انعزال الأمشاج: ينتج الأب الأول أمشاج [${g1.join(', ')}] والأب الثاني [${g2.join(', ')}].\n2. التلقيح العشوائي يشكل 4 احتمالات داخل شبكة بانيت.\n3. النسبة المظهرية الناتجة: ${domCount} سائد مقابل ${recCount} متنحٍ (النسبة الكلاسيكية ${domCount}:${recCount}).`
            : `1. Law of Segregation: P1 produces gametes [${g1.join(', ')}] and P2 produces [${g2.join(', ')}].\n2. Fertilization generates 4 equally probable offspring boxes.\n3. Phenotypic distribution: ${domCount} dominant : ${recCount} recessive (Ratio ${domCount}:${recCount}).`;
    }
}

function normGt(gt) {
    return gt.split('').sort().join('');
}

function isDom(gt) {
    return gt.toUpperCase().includes('A') && gt !== 'aa' && gt !== 'bb' && gt !== 'pp' && gt !== 'rr' && gt !== 'yy' && gt !== 'tt';
}

// DNA Explorer Analysis
function analyzeDnaWeb() {
    const input = document.getElementById("dnaInputWeb").value.toUpperCase().replace(/\s/g, '') || "ATGCGATACGCTTGA";
    const comp = input.split('').map(c => c === 'A' ? 'T' : c === 'T' ? 'A' : c === 'C' ? 'G' : c === 'G' ? 'C' : 'N').join('');
    const mrna = input.replace(/T/g, 'U');

    // Render chips
    document.getElementById("senseStrandChips").innerHTML = input.split('').map(c => `
        <div class="base-chip base-${c.toLowerCase()}">${c}</div>
    `).join('');

    document.getElementById("complementaryStrandChips").innerHTML = comp.split('').map(c => `
        <div class="base-chip base-${c.toLowerCase()}">${c}</div>
    `).join('');

    document.getElementById("mrnaDisplay").textContent = `5'- ${mrna} -3'`;

    // Translate Codons
    let codons = [];
    for (let i = 0; i + 2 < mrna.length; i += 3) {
        let triplet = mrna.substring(i, i + 3);
        codons.push(triplet);
    }

    document.getElementById("aminoAcidsRow").innerHTML = codons.map(codon => {
        let aa = GENETIC_CODE[codon] || { code: "???", nameAr: "غير محدد", nameEn: "Unknown", color: "#64748b" };
        return `
            <div class="aa-chip" style="border-color:${aa.color}">
                <span class="aa-code" style="color:${aa.color}">${aa.code}</span>
                <span class="aa-name">${currentLang==='ar'?aa.nameAr:aa.nameEn}</span>
            </div>
        `;
    }).join('');
}

function setDnaPreset(seq) {
    document.getElementById("dnaInputWeb").value = seq;
    analyzeDnaWeb();
}

// Breeding Simulator
function runBreedingSimulationWeb() {
    const N = parseInt(document.getElementById("simSampleSize").value) || 100;
    const p1 = document.getElementById("simP1").value || "Aa";
    const p2 = document.getElementById("simP2").value || "Aa";

    const g1 = [p1[0] || 'A', p1[1] || 'a'];
    const g2 = [p2[0] || 'A', p2[1] || 'a'];

    let observedDom = 0;
    let observedRec = 0;

    for (let i = 0; i < N; i++) {
        let a1 = g1[Math.floor(Math.random() * 2)];
        let a2 = g2[Math.floor(Math.random() * 2)];
        let gt = (a1 + a2).toLowerCase();
        if (gt.includes('a') && gt !== 'aa') {
            observedDom++;
        } else {
            observedRec++;
        }
    }

    const expectedDom = N * 0.75;
    const expectedRec = N * 0.25;

    let chiSq = Math.pow(observedDom - expectedDom, 2) / expectedDom + Math.pow(observedRec - expectedRec, 2) / expectedRec;

    document.getElementById("simCountBadge").textContent = `N = ${N}`;
    document.getElementById("simComparisonBars").innerHTML = `
        <div class="bar-item">
            <div class="bar-labels">
                <span>سائد مرصود (Observed Dominant): ${observedDom} (${((observedDom/N)*100).toFixed(1)}%)</span>
                <span>المتوقع النظري: 75%</span>
            </div>
            <div class="progress-track"><div class="progress-fill" style="width:${(observedDom/N)*100}%"></div></div>
        </div>
        <div class="bar-item">
            <div class="bar-labels">
                <span>متنحٍ مرصود (Observed Recessive): ${observedRec} (${((observedRec/N)*100).toFixed(1)}%)</span>
                <span>المتوقع النظري: 25%</span>
            </div>
            <div class="progress-track"><div class="progress-fill" style="width:${(observedRec/N)*100}%;background:#ef4444"></div></div>
        </div>
    `;

    document.getElementById("chiValue").textContent = chiSq.toFixed(2);
    document.getElementById("chiExplanation").textContent = chiSq < 3.84
        ? (currentLang==='ar' ? "قيمة p > 0.05: النتائج تتوافق ممتازاً مع التوقعات المندلية ولا يوجد انحراف ذو دلالة إحصائية." : "p > 0.05: Outcomes align with Mendelian expectations without significant statistical deviation.")
        : (currentLang==='ar' ? "قيمة p <= 0.05: انحراف إحصائي عشوائي ناتج عن حجم العينة والصدفة الطبيعية." : "p <= 0.05: Significant deviation due to sampling noise and genetic variance.");
}

// Scenarios & Mutations Renderers
function renderScenarios() {
    const row = document.getElementById("solverScenariosRow");
    if (!row) return;
    row.innerHTML = SCENARIOS.map((sc, i) => `
        <button class="chip ${i===0?'active':''}" onclick="selectScenario(${i})">${currentLang==='ar'?sc.titleAr:sc.titleEn}</button>
    `).join('');
    selectScenario(0);
}

function selectScenario(idx) {
    const sc = SCENARIOS[idx];
    document.getElementById("scPatternBadge").textContent = currentLang==='ar'?sc.patternAr:sc.patternEn;
    document.getElementById("scTitle").textContent = currentLang==='ar'?sc.titleAr:sc.titleEn;
    document.getElementById("scDesc").textContent = currentLang==='ar'?sc.descAr:sc.descEn;
    document.getElementById("scSolution").textContent = currentLang==='ar'?sc.solutionAr:sc.solutionEn;
}

function solveCustomProblem() {
    const text = document.getElementById("customProblemInput").value;
    if (!text.trim()) return;
    switchTab("assistant");
    sendQuickPrompt(text);
}

function renderMutations() {
    const row = document.getElementById("mutationsPresetsRow");
    if (!row) return;
    row.innerHTML = MUTATIONS.map((m, i) => `
        <button class="chip ${i===0?'active':''}" onclick="selectMutation(${i})">${currentLang==='ar'?m.titleAr:m.titleEn}</button>
    `).join('');
    selectMutation(0);
}

function selectMutation(idx) {
    const m = MUTATIONS[idx];
    document.getElementById("mutTypeBadge").textContent = currentLang==='ar'?m.typeAr:m.typeEn;
    document.getElementById("mutPosBadge").textContent = `${currentLang==='ar'?'الموقع: ':'Position: '}${m.pos}`;
    document.getElementById("mutTitle").textContent = currentLang==='ar'?m.titleAr:m.titleEn;
    document.getElementById("mutDesc").textContent = currentLang==='ar'?m.descAr:m.descEn;

    document.getElementById("originalSeqBadges").innerHTML = m.originalDna.split('').map(c => `
        <div class="base-chip base-${c.toLowerCase()}">${c}</div>
    `).join('');

    document.getElementById("mutatedSeqBadges").innerHTML = m.mutatedDna.split('').map((c, i) => `
        <div class="base-chip base-${c.toLowerCase()}" style="${i===m.pos?'border:2px solid #ef4444;box-shadow:0 0 10px #ef4444':''}">${c}</div>
    `).join('');

    document.getElementById("mutImpactText").textContent = currentLang==='ar' ? m.descAr : m.descEn;
}

// Learning Center
function initLearningCenter() {
    const container = document.getElementById("lessonsSubpane");
    if (!container) return;
    const lessons = [
        { titleAr: "مقدمة في علم الوراثة والتوريث", titleEn: "Introduction to Genetics & Heredity", track: "Beginner", time: 8 },
        { titleAr: "الحمض النووي DNA: المخطط الأساسي للحياة", titleEn: "DNA: The Blueprint of Life", track: "Beginner", time: 10 },
        { titleAr: "الجينات، الأليلات، والكروموسومات", titleEn: "Genes, Alleles & Chromosomes", track: "Beginner", time: 9 },
        { titleAr: "الانقسام المتساوي مقابل المنصف", titleEn: "Mitosis vs Meiosis", track: "Intermediate", time: 12 },
        { titleAr: "قوانين مندل في الوراثة", titleEn: "Mendel's Laws of Inheritance", track: "Intermediate", time: 11 },
        { titleAr: "مربعات بانيت وحساب الاحتمالات", titleEn: "Punnett Squares & Probability", track: "Intermediate", time: 10 },
        { titleAr: "الوراثة اللا مندلية والسيادة المشتركة", titleEn: "Non-Mendelian Inheritance", track: "Advanced", time: 14 },
        { titleAr: "الطفرات والتنوع الجيني", titleEn: "Mutations & Genetic Diversity", track: "Advanced", time: 13 },
        { titleAr: "التكنولوجيا الحيوية الحديثة: كريسبر و PCR", titleEn: "Modern Biotechnology: CRISPR & PCR", track: "Advanced", time: 15 }
    ];

    container.innerHTML = lessons.map(l => `
        <div class="tool-card glass-card">
            <div class="tool-icon-box green-box">📖</div>
            <div class="tool-info">
                <span class="badge cyan-badge">${l.track} • ${l.time} min</span>
                <h4>${currentLang==='ar'?l.titleAr:l.titleEn}</h4>
            </div>
        </div>
    `).join('');
}

function switchLearningSubtab(tab) {
    document.getElementById("btnLessonsTab").classList.toggle("active", tab === 'lessons');
    document.getElementById("btnGlossaryTab").classList.toggle("active", tab === 'glossary');
    document.getElementById("lessonsSubpane").classList.toggle("hidden", tab !== 'lessons');
    document.getElementById("glossarySubpane").classList.toggle("hidden", tab !== 'glossary');
}

// Quiz System
function startQuizWeb(topic) {
    quizCurrentIdx = 0;
    quizScore = 0;
    quizIsChecked = false;
    quizSelectedOpt = null;
    renderQuizQuestion();
}

function renderQuizQuestion() {
    const q = QUIZ_BANK[quizCurrentIdx];
    document.getElementById("quizQCount").textContent = `${currentLang==='ar'?'السؤال':'Question'} ${quizCurrentIdx + 1} / ${QUIZ_BANK.length}`;
    document.getElementById("quizScoreBadge").textContent = `${currentLang==='ar'?'الدرجة: ':'Score: '}${quizScore}`;
    document.getElementById("quizQuestionText").textContent = currentLang==='ar'?q.qAr:q.qEn;

    const opts = currentLang==='ar'?q.optsAr:q.optsEn;
    document.getElementById("quizOptionsList").innerHTML = opts.map((opt, i) => `
        <button class="btn btn-secondary quiz-opt-btn" onclick="selectQuizOpt(${i})" id="qopt-${i}">
            <span class="opt-letter">${String.fromCharCode(65 + i)}.</span>
            <span>${opt}</span>
        </button>
    `).join('');

    document.getElementById("quizFeedbackBox").classList.add("hidden");
    document.getElementById("quizActionBtn").textContent = currentLang==='ar' ? 'تأكيد الإجابة' : 'Submit Answer';
}

function selectQuizOpt(idx) {
    if (quizIsChecked) return;
    quizSelectedOpt = idx;
    document.querySelectorAll(".quiz-opt-btn").forEach((b, i) => {
        b.style.borderColor = i === idx ? 'var(--cyan)' : 'var(--border-dark)';
    });
}

function handleQuizAction() {
    if (!quizIsChecked) {
        if (quizSelectedOpt === null) return;
        quizIsChecked = true;
        const q = QUIZ_BANK[quizCurrentIdx];
        const isCorrect = quizSelectedOpt === q.correct;
        if (isCorrect) quizScore++;

        const fb = document.getElementById("quizFeedbackBox");
        fb.classList.remove("hidden");
        fb.style.borderColor = isCorrect ? 'var(--green)' : 'var(--coral)';
        fb.innerHTML = `
            <strong>${isCorrect ? (currentLang==='ar'?'إجابة صحيحة وممتازة! 🎉':'Correct! Well done! 🎉') : (currentLang==='ar'?'إجابة غير صحيحة!':'Incorrect!')}</strong>
            <p>${currentLang==='ar'?q.expAr:q.expEn}</p>
        `;
        document.getElementById("quizActionBtn").textContent = quizCurrentIdx + 1 < QUIZ_BANK.length
            ? (currentLang==='ar' ? 'السؤال التالي ➔' : 'Next Question ➔')
            : (currentLang==='ar' ? 'إنهاء الاختبار 🏆' : 'Finish Quiz 🏆');
    } else {
        quizCurrentIdx++;
        if (quizCurrentIdx < QUIZ_BANK.length) {
            quizIsChecked = false;
            quizSelectedOpt = null;
            renderQuizQuestion();
        } else {
            alert(currentLang==='ar' ? `تهانينا! أنهيت الاختبار بدرجة ${quizScore} من ${QUIZ_BANK.length}` : `Congratulations! You scored ${quizScore} / ${QUIZ_BANK.length}`);
            startQuizWeb('All');
        }
    }
}

// AI Chat Assistant
function sendQuickPrompt(prompt) {
    document.getElementById("aiInput").value = prompt;
    sendAiMessage();
}

function sendAiMessage() {
    const input = document.getElementById("aiInput");
    const text = input.value.trim();
    if (!text) return;
    input.value = "";

    const msgContainer = document.getElementById("chatMessages");
    // Add user message
    msgContainer.innerHTML += `
        <div class="chat-bubble user-bubble">
            <span class="sender-name">${currentLang==='ar'?'أنت':'You'}</span>
            <p>${text}</p>
        </div>
    `;
    msgContainer.scrollTop = msgContainer.scrollHeight;

    // Simulate intelligent biology engine response
    setTimeout(() => {
        let reply = "";
        let lower = text.toLowerCase();
        if (lower.includes("aa") || lower.includes("تزاوج") || lower.includes("cross")) {
            reply = currentLang==='ar'
                ? "🧬 **حل مسألة التزاوج (Aa × Aa)**:\n\n• الأمشاج: ينتج كل أب مشيجين (A و a).\n• التراكيب الناتجة: 1 AA (25%) و 2 Aa (50%) و 1 aa (25%).\n• النسبة المظهرية: 75% صفة سائدة إلى 25% صفة متنحية (بنسبة 3:1).\n• التفسير: تطبيق مباشر لقانون مندل الأول (انعزال الصفات)."
                : "🧬 **Cross Solution (Aa x Aa)**:\n\n• Gametes: Both parents produce A and a gametes.\n• Genotypes: 25% AA, 50% Aa, 25% aa.\n• Phenotypes: 75% dominant, 25% recessive (3:1 ratio).\n• Principle: Law of Segregation.";
        } else {
            reply = currentLang==='ar'
                ? "🧬 **GeneLab AI Assistant**:\n\nتم استقبال استفسارك الوراثي. يتميز علم الوراثة بدقة القوانين الرياضية للجزيئات الحيوية. يمكنك استخدام أدوات المختبر لحساب مربعات بانيت مباشرة، أو استعراض سلاسل الـ DNA وترجمتها لكودونات."
                : "🧬 **GeneLab AI Assistant**:\n\nYour query has been analyzed. Genetics relies on molecular biochemistry and probabilistic segregation. Try testing our interactive Punnett simulator or DNA codon translation workbench!";
        }

        msgContainer.innerHTML += `
            <div class="chat-bubble ai-bubble">
                <span class="sender-name">GeneLab AI 🧬</span>
                <p style="white-space:pre-line">${reply}</p>
            </div>
        `;
        msgContainer.scrollTop = msgContainer.scrollHeight;
    }, 600);
}

// Animated Canvas Double Helix
function initDnaCanvas(canvasId) {
    const canvas = document.getElementById(canvasId);
    if (!canvas) return;
    const ctx = canvas.getContext("2d");
    let phase = 0;

    function render() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        const w = canvas.width;
        const h = canvas.height;
        const cx = w / 2;
        const amp = w * 0.35;
        const steps = 12;
        const stepY = h / (steps + 1);

        for (let i = 0; i <= steps; i++) {
            const y = stepY * (i + 0.5);
            const angle = phase + (i * 0.45);
            const xOff = Math.sin(angle) * amp;
            const depth = (Math.sin(angle + Math.PI / 2) + 1) / 2;

            const x1 = cx - xOff;
            const x2 = cx + xOff;

            // Rung
            ctx.beginPath();
            ctx.moveTo(x1, y);
            ctx.lineTo(x2, y);
            ctx.strokeStyle = `rgba(0, 240, 255, ${0.2 + depth * 0.7})`;
            ctx.lineWidth = 2 + depth * 2;
            ctx.stroke();

            // Nodes
            ctx.beginPath();
            ctx.arc(x1, y, 4 + depth * 2, 0, Math.PI * 2);
            ctx.fillStyle = "#00f0ff";
            ctx.fill();

            ctx.beginPath();
            ctx.arc(x2, y, 4 + depth * 2, 0, Math.PI * 2);
            ctx.fillStyle = "#a855f7";
            ctx.fill();
        }

        phase += 0.03;
        requestAnimationFrame(render);
    }
    render();
}
