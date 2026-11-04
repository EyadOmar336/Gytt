package com.example.data.content

import com.example.data.model.*

object CuratedGeneticsData {

    val LESSONS: List<Lesson> = listOf(
        Lesson(
            id = "lesson_1",
            track = "Beginner",
            trackAr = "مبتدئ",
            titleEn = "Introduction to Genetics & Heredity",
            titleAr = "مقدمة في علم الوراثة والتوريث",
            durationMin = 8,
            iconName = "biotech",
            summaryEn = "Discover how physical and biochemical traits pass from parents to offspring through biological mechanisms.",
            summaryAr = "اكتشف كيف تنتقل الصفات الجسدية والكيميائية الحيوية من الآباء إلى الأبناء عبر الآليات الحيوية.",
            sections = listOf(
                LessonSection(
                    headingEn = "What is Genetics?",
                    headingAr = "ما هو علم الوراثة؟",
                    contentEn = "Genetics is the scientific study of genes, genetic variation, and heredity in organisms. Every living cell contains instructions written in nucleic acids that determine how an organism develops, functions, and survives.",
                    contentAr = "علم الوراثة هو الفرع العلمي الذي يدرس الجينات، والتنوع الوراثي، وانتقال الصفات من جيل إلى جيل. تحتوي كل خلية حية على تعليمات بيولوجية تحدد شكل الكائن الحي ووظائفه الحيوية.",
                    keyTakeawayEn = "Genetics explains why offspring resemble their parents while maintaining unique individual diversity.",
                    keyTakeawayAr = "يفسر علم الوراثة لماذا يشبه الأبناء آباءهم مع الحفاظ على التنوع الفردي الفريد."
                ),
                LessonSection(
                    headingEn = "Gregor Mendel: Father of Genetics",
                    headingAr = "جريجور مندل: مؤسس علم الوراثة",
                    contentEn = "In the 1860s, Gregor Mendel conducted groundbreaking hybridization experiments on garden pea plants (Pisum sativum). He established that traits are inherited as discrete physical units (now known as genes) rather than blending like paint.",
                    contentAr = "في ستينيات القرن التاسع عشر، أجرى الراهب النمساوي جريجور مندل تجارب تاريخية على نباتات البازلاء، وأثبت أن الصفات تورث كوحدات منفصلة (الجينات) بدلاً من أن تمتزج مثل الألوان.",
                    keyTakeawayEn = "Traits are particulate and follow mathematical laws of probability.",
                    keyTakeawayAr = "الصفات الوراثية تنتقل في هيئة وحدات مادية ثابتة تخضع لقوانين الاحتمالات الرياضية."
                )
            ),
            quickQuiz = listOf(
                QuizQuestion(
                    id = "q_l1_1",
                    questionEn = "Which organism did Gregor Mendel study to discover the principles of inheritance?",
                    questionAr = "ما الكائن الحي الذي درسه جريجور مندل لاكتشاف مبادئ التوريث؟",
                    optionsEn = listOf("Fruit flies (Drosophila)", "Pea plants (Pisum sativum)", "E. coli bacteria", "Mice"),
                    optionsAr = listOf("ذبابة الفاكهة (دروسوفيلا)", "نبات البازلاء (Pisum sativum)", "بكتيريا إشريكية قولونية", "الفئران"),
                    correctIndex = 1,
                    explanationEn = "Mendel worked with garden pea plants due to their distinct characteristics and ease of controlled pollination.",
                    explanationAr = "اختار مندل نبات البازلاء لسهولة زراعته ووضوح صفاته وقدرته على التلقيح الذاتي والخلطي.",
                    topic = "Introduction"
                )
            )
        ),
        Lesson(
            id = "lesson_2",
            track = "Beginner",
            trackAr = "مبتدئ",
            titleEn = "DNA: The Blueprint of Life",
            titleAr = "الحمض النووي DNA: المخطط الأساسي للحياة",
            durationMin = 10,
            iconName = "dna",
            summaryEn = "Explore the double-helix architecture, antiparallel strands, and the four nucleotide bases: A, T, C, and G.",
            summaryAr = "استكشف بنية اللولب المزدوج، والسلاسل المتعاكسة، والقواعد النيتروجينية الأربع: A و T و C و G.",
            sections = listOf(
                LessonSection(
                    headingEn = "The Double Helix Architecture",
                    headingAr = "هيكل اللولب المزدوج",
                    contentEn = "Deoxyribonucleic acid (DNA) is formed by two antiparallel strands coiled around a common axis. The backbone consists of alternating deoxyribose sugar and phosphate groups, connected by phosphodiester bonds.",
                    contentAr = "يتكون الحمض النووي الريبوزي منقوص الأكسجين (DNA) من سلسلتين متعاكستين تلتفان حول محور مركزي. يتكون الهيكل الخارجي من سكر ريبوزي منقوص الأكسجين ومجموعات فوسفات.",
                    keyTakeawayEn = "DNA strands run antiparallel: 5' to 3' and 3' to 5'.",
                    keyTakeawayAr = "سلسلتا الـ DNA تسيران في اتجاهين متعاكسين: من '5 إلى '3 ومن '3 إلى '5."
                ),
                LessonSection(
                    headingEn = "Complementary Base Pairing (Chargaff's Rule)",
                    headingAr = "تكامل القواعد النيتروجينية (قاعدة تشارجاف)",
                    contentEn = "Adenine (A) always pairs with Thymine (T) via two hydrogen bonds. Cytosine (C) pairs with Guanine (G) via three hydrogen bonds. This complementary pairing ensures accurate replication and transcription.",
                    contentAr = "يرتبط الأدينين (A) دائماً بالثايمين (T) برابطتين هيدروجينيتين، بينما يرتبط السيتوسين (C) بالجوانين (G) بثلاث روابط هيدروجينية.",
                    keyTakeawayEn = "A = T (2 bonds) and G ≡ C (3 bonds).",
                    keyTakeawayAr = "A مع T برابطتين، و C مع G بثلاث روابط هيدروجينية قوية."
                )
            ),
            quickQuiz = listOf(
                QuizQuestion(
                    id = "q_l2_1",
                    questionEn = "If a DNA strand has the sequence 5'-AGCT-3', what is the complementary strand in the 3'-5' direction?",
                    questionAr = "إذا كانت سلسلة الـ DNA هي '5-AGCT-3'، فما هي السلسلة المكملة في اتجاه '3 إلى '5؟",
                    optionsEn = listOf("3'-TCGA-5'", "3'-AGCT-5'", "3'-UCGA-5'", "3'-CTAG-5'"),
                    optionsAr = listOf("'3-TCGA-5'", "'3-AGCT-5'", "'3-UCGA-5'", "'3-CTAG-5'"),
                    correctIndex = 0,
                    explanationEn = "A pairs with T, G pairs with C, C pairs with G, and T pairs with A: resulting in TCGA.",
                    explanationAr = "الأدينين A يقابله T، والجوانين G يقابله C، والسيتوسين C يقابله G، والثايمين T يقابله A.",
                    topic = "DNA"
                )
            )
        ),
        Lesson(
            id = "lesson_3",
            track = "Beginner",
            trackAr = "مبتدئ",
            titleEn = "Genes, Alleles & Chromosomes",
            titleAr = "الجينات، الأليلات، والكروموسومات",
            durationMin = 9,
            iconName = "account_tree",
            summaryEn = "Learn how DNA condenses into chromosomes and how alternative allele variants produce distinct traits.",
            summaryAr = "تعرف على كيفية تكثف الحمض النووي إلى كروموسومات وكيف تعطي الأليلات المختلفة صفات وراثية متنوعة.",
            sections = listOf(
                LessonSection(
                    headingEn = "From DNA to Chromosome",
                    headingAr = "من الـ DNA إلى الكروموسوم",
                    contentEn = "Human cells pack 2 meters of DNA inside a microscopic nucleus by wrapping it tightly around histone proteins into nucleosomes, forming chromatin and condensed chromosomes during cell division.",
                    contentAr = "تحتوي كل خلية بشرية على مترين من الـ DNA ملفوفة بإحكام حول بروتينات الهستونات لتشكل الكروماتين والكروموسومات المنظمة داخل النواة.",
                    keyTakeawayEn = "Humans carry 23 pairs (46 total) of chromosomes in somatic cells.",
                    keyTakeawayAr = "يمتلك الإنسان 23 زوجاً (46 كروموسوماً) في كل خلية جسدية."
                ),
                LessonSection(
                    headingEn = "Genotype vs Phenotype",
                    headingAr = "الطراز الجيني مقابل الطراز المظهري",
                    contentEn = "A genotype is the exact genetic makeup (e.g., homozygous dominant AA, heterozygous Aa, or homozygous recessive aa). The phenotype is the observable physical or physiological expression (e.g., purple flowers or brown eyes).",
                    contentAr = "الطراز الجيني هو التركيب الوراثي الداخلي (مثل AA أو Aa أو aa)، بينما الطراز المظهري هو الصفة المشاهدة بالعين أو الملموسة فسيولوجياً (مثل لون العينين أو فصيلة الدم).",
                    keyTakeawayEn = "Phenotype = Genotype + Environmental Interaction.",
                    keyTakeawayAr = "الطراز المظهري = الطراز الجيني + تفاعل البيئة المحيطة."
                )
            ),
            quickQuiz = listOf(
                QuizQuestion(
                    id = "q_l3_1",
                    questionEn = "What is the term for an organism carrying two identical alleles for a single gene (e.g., BB or bb)?",
                    questionAr = "ما المصطلح العلمي للكائن الذي يحمل أليلين متطابقين لجين معين (مثل BB أو bb)؟",
                    optionsEn = listOf("Homozygous (Purebred)", "Heterozygous (Hybrid)", "Hemizygous", "Polygenic"),
                    optionsAr = listOf("متماثل الأليلات (نقي)", "متباين الأليلات (هجين)", "نصف أليلي", "متعدد الجينات"),
                    correctIndex = 0,
                    explanationEn = "Homozygous means both alleles at a locus are identical (either dominant BB or recessive bb).",
                    explanationAr = "متماثل الأليلات (Homozygous) يعني أن نسختي الجين متطابقتان إما كلاهما سائد أو كلاهما متنحٍ.",
                    topic = "Alleles"
                )
            )
        ),
        Lesson(
            id = "lesson_4",
            track = "Intermediate",
            trackAr = "متوسط",
            titleEn = "Mitosis vs Meiosis & Gametogenesis",
            titleAr = "الانقسام المتساوي مقابل الانقسام المنصف وتكوين الأمشاج",
            durationMin = 12,
            iconName = "donut_large",
            summaryEn = "Understand how somatic cells replicate identically through mitosis, while meiosis produces genetic diversity in haploid gametes.",
            summaryAr = "افهم كيف تتضاعف الخلايا الجسدية بالانقسام المتساوي، بينما يخلق الانقسام المنصف التنوع الوراثي في الأمشاج أحادية المجموعة.",
            sections = listOf(
                LessonSection(
                    headingEn = "Cellular Division Roles",
                    headingAr = "أدوار الانقسام الخلوي",
                    contentEn = "Mitosis produces two genetically identical diploid (2n) daughter cells for growth and tissue repair. Meiosis produces four non-identical haploid (n) gametes (sperm and egg cells).",
                    contentAr = "ينتج الانقسام المتساوي خليتين متطابقتين وراثياً ثنائية المجموعة الكروموسومية (2n) للنمو والتعويض، بينما ينتج الانقسام المنصف 4 أمشاج أحادية المجموعة (n) للتكاثر الجنسي.",
                    keyTakeawayEn = "Mitosis: 1 division -> 2 identical cells. Meiosis: 2 divisions -> 4 diverse gametes.",
                    keyTakeawayAr = "المتساوي: انقسام واحد ينتج خليتين متطابقتين. المنصف: انقسامان ينتجان 4 خلايا متنوعة."
                ),
                LessonSection(
                    headingEn = "Crossing Over (Recombination)",
                    headingAr = "العبور الجيني (التوليف الوراثي)",
                    contentEn = "During Prophase I of Meiosis, homologous non-sister chromatids physically exchange genetic segments at chiasmata. This creates novel combinations of maternal and paternal alleles.",
                    contentAr = "خلال الطور التمهيدي الأول من الانقسام المنصف، يحدث تبادل لأجزاء من المادة الوراثية بين الكروماتيدات غير الشقيقة (العبور الجيني)، مما يضمن التنوع الوراثي اللانهائي للأجيال القادمة.",
                    keyTakeawayEn = "Crossing over shuffles alleles along chromosomes.",
                    keyTakeawayAr = "العبور الجيني يعيد خلط الأليلات على طول الكروموسومات."
                )
            ),
            quickQuiz = listOf(
                QuizQuestion(
                    id = "q_l4_1",
                    questionEn = "During which stage of meiosis does crossing over occur?",
                    questionAr = "في أي مرحلة من الانقسام المنصف يحدث العبور الجيني؟",
                    optionsEn = listOf("Prophase I", "Metaphase II", "Anaphase I", "Telophase II"),
                    optionsAr = listOf("الطور التمهيدي الأول (Prophase I)", "الطور الاستوائي الثاني", "الطور الانفصالي الأول", "الطور النهائي الثاني"),
                    correctIndex = 0,
                    explanationEn = "Crossing over takes place during Prophase I when homologous chromosomes synapse into tetrads.",
                    explanationAr = "يحدث العبور الجيني في الطور التمهيدي الأول حيث تصطف الكروموسومات المتماثلة مشكلة الرباعيات.",
                    topic = "Meiosis"
                )
            )
        ),
        Lesson(
            id = "lesson_5",
            track = "Intermediate",
            trackAr = "متوسط",
            titleEn = "Mendel's Laws of Inheritance",
            titleAr = "قوانين مندل في الوراثة",
            durationMin = 11,
            iconName = "balance",
            summaryEn = "Master the Law of Segregation and the Law of Independent Assortment with classic ratios.",
            summaryAr = "أتقن قانون انعزال الصفات وقانون التوزيع الحر مع النسب الرياضية الكلاسيكية الشهيرة.",
            sections = listOf(
                LessonSection(
                    headingEn = "1st Law: Law of Segregation",
                    headingAr = "القانون الأول: قانون انعزال الصفات",
                    contentEn = "Each organism possesses two alleles for each gene. These alleles separate during gamete formation, so that each gamete carries only one allele for each locus.",
                    contentAr = "يمتلك كل فرد زوجاً من الأليلات لكل صفة، وينفصل هذان الأليلان عند تكوين الأمشاج بحيث يحتوي كل مشيج على أليل واحد فقط لكل جين.",
                    keyTakeawayEn = "Monohybrid heterozygous cross (Aa x Aa) produces a 3:1 phenotypic ratio.",
                    keyTakeawayAr = "التزاوج الهجين الأحادي (Aa × Aa) ينتج نسبة مظهرية 3 سائد : 1 متنحٍ."
                ),
                LessonSection(
                    headingEn = "2nd Law: Law of Independent Assortment",
                    headingAr = "القانون الثاني: قانون التوزيع الحر",
                    contentEn = "Genes located on different non-homologous chromosomes assort independently of one another during gamete production, generating the classic 9:3:3:1 phenotypic ratio in dihybrid crosses.",
                    contentAr = "تتوزع أليلات الجينات المختلفة الموجودة على كروموسومات غير متماثلة توزيعاً حراً ومستقلاً عن بعضها البعض عند تكوين الأمشاج، لتعطي نسبة 9:3:3:1 في الهجين الثنائي.",
                    keyTakeawayEn = "Dihybrid cross (AaBb x AaBb) yields a 9:3:3:1 phenotypic distribution.",
                    keyTakeawayAr = "التلقيح الثنائي الهجين (AaBb × AaBb) يعطي النسبة المظهرية 9:3:3:1."
                )
            ),
            quickQuiz = listOf(
                QuizQuestion(
                    id = "q_l5_1",
                    questionEn = "What is the expected phenotypic ratio in the offspring of an AaBb x AaBb dihybrid cross?",
                    questionAr = "ما هي النسبة المظهرية المتوقعة لأبناء تزاوج هجين ثنائي AaBb × AaBb؟",
                    optionsEn = listOf("9:3:3:1", "3:1", "1:2:1", "1:1:1:1"),
                    optionsAr = listOf("9:3:3:1", "3:1", "1:2:1", "1:1:1:1"),
                    correctIndex = 0,
                    explanationEn = "Independent assortment of two unlinked heterozygous genes results in 9 double dominant, 3 dom/rec, 3 rec/dom, and 1 double recessive.",
                    explanationAr = "التوزيع المستقل لجينين هجينين غير مرتبطين ينتج النسبة المظهرية الكلاسيكية 9:3:3:1.",
                    topic = "Mendel"
                )
            )
        ),
        Lesson(
            id = "lesson_6",
            track = "Intermediate",
            trackAr = "متوسط",
            titleEn = "Punnett Squares & Probability Calculations",
            titleAr = "مربعات بانيت وحساب الاحتمالات",
            durationMin = 10,
            iconName = "grid_view",
            summaryEn = "Learn to construct monohybrid and dihybrid Punnett squares and calculate genotypic and phenotypic frequencies.",
            summaryAr = "تعلم كيفية بناء مربعات بانيت الأحادية والثنائية وحساب التكرارات الجينية والمظهرية بدقة.",
            sections = listOf(
                LessonSection(
                    headingEn = "Constructing a Punnett Grid",
                    headingAr = "بناء شبكة بانيت",
                    contentEn = "Created by Reginald Punnett in 1905, the Punnett Square organizes maternal and paternal gametes along the perimeter. Each internal box represents an equally probable fertilization event.",
                    contentAr = "ابتكر العالم ريجينالد بانيت عام 1905 هذا المخطط الشبكي لترتيب أمشاج الأبوين على الحواف وتمثيل أحداث الإخصاب المحتملة داخل المربعات.",
                    keyTakeawayEn = "Grid cells represent the product of gamete probabilities.",
                    keyTakeawayAr = "كل خلية تمثل حاصل ضرب احتمالية مشيج الأب في مشيج الأم."
                )
            ),
            quickQuiz = listOf(
                QuizQuestion(
                    id = "q_l6_1",
                    questionEn = "In a cross between a homozygous dominant parent (AA) and a heterozygous parent (Aa), what percent of offspring will be heterozygous (Aa)?",
                    questionAr = "في تزاوج بين أب سائد نقي (AA) وأب هجين (Aa)، ما نسبة الأبناء الهجناء (Aa)؟",
                    optionsEn = listOf("50%", "25%", "75%", "100%"),
                    optionsAr = listOf("50%", "25%", "75%", "100%"),
                    correctIndex = 0,
                    explanationEn = "AA x Aa results in 2 AA (50%) and 2 Aa (50%). All show the dominant phenotype.",
                    explanationAr = "تزاوج AA مع Aa ينتج 2 AA بنسبة 50% و 2 Aa بنسبة 50% وكلاهما ذو مظهر سائد.",
                    topic = "Punnett"
                )
            )
        ),
        Lesson(
            id = "lesson_7",
            track = "Advanced",
            trackAr = "متقدم",
            titleEn = "Non-Mendelian & Complex Inheritance",
            titleAr = "الوراثة اللا مندلية والوراثة المعقدة",
            durationMin = 14,
            iconName = "schema",
            summaryEn = "Explore incomplete dominance, codominance (ABO blood groups), and sex-linked traits.",
            summaryAr = "استكشف السيادة غير التامة، السيادة المشتركة (فصائل دم ABO)، والصفات المرتبطة بالجنس.",
            sections = listOf(
                LessonSection(
                    headingEn = "Codominance & Incomplete Dominance",
                    headingAr = "السيادة المشتركة والسيادة غير التامة",
                    contentEn = "In incomplete dominance, the heterozygote exhibits an intermediate blended phenotype (e.g., Red x White snapdragons = Pink). In codominance, both alleles are fully and distinctly expressed (e.g., Blood Type AB shows both A and B glycoproteins).",
                    contentAr = "في السيادة غير التامة يظهر الهجين صفة وسطية ممتزجة (أزهار بيضاء مع حمراء تعطي وردية)، بينما في السيادة المشتركة يظهر كلا الأليلين معاً بشكل واضح (مثل فصيلة الدم AB حيث يوجد كلا المستضدين A و B).",
                    keyTakeawayEn = "Incomplete dominance blends; codominance expresses both concurrently.",
                    keyTakeawayAr = "السيادة غير التامة تدمج المظهر، بينما السيادة المشتركة تعبر عن الأليلين معاً."
                ),
                LessonSection(
                    headingEn = "Sex-Linked Inheritance",
                    headingAr = "الصفات المرتبطة بالجنس",
                    contentEn = "Genes located on the X chromosome follow sex-linked inheritance patterns. Males (XY) are hemizygous and express recessive X-linked conditions (such as red-green color blindness and hemophilia) much more frequently than females (XX).",
                    contentAr = "توجد بعض الجينات على الكروموسوم الجنسي X. وبما أن الذكور يمتلكون كروموسوم X واحداً فقط (XY)، فإنهم يعبرون عن الصفات المتنحية (مثل عمى الألوان ونزف الدم الهيموفيليا) بمعدل أكبر بكثير من الإناث.",
                    keyTakeawayEn = "Fathers pass their X chromosome exclusively to their daughters.",
                    keyTakeawayAr = "ينقل الأب كروموسوم X إلى بناته فقط، بينما ينقل كروموسوم Y إلى أبنائه الذكور."
                )
            ),
            quickQuiz = listOf(
                QuizQuestion(
                    id = "q_l7_1",
                    questionEn = "If a mother with type A blood (genotype I^A i) and a father with type B blood (genotype I^B i) have children, which blood types are possible?",
                    questionAr = "إذا كان للأم فصيلة دم A بطراز جيني (I^A i) وللأب فصيلة دم B بطراز جيني (I^B i)، فما هي فصائل الدم الممكنة للأبناء؟",
                    optionsEn = listOf("A, B, AB, and O (All four)", "Only AB", "Only A and B", "Only AB and O"),
                    optionsAr = listOf("A و B و AB و O (جميع الفصائل الأربع)", "فقط AB", "فقط A و B", "فقط AB و O"),
                    correctIndex = 0,
                    explanationEn = "Cross I^A i x I^B i produces I^A I^B (type AB), I^A i (type A), I^B i (type B), and ii (type O), each with a 25% probability.",
                    explanationAr = "التزاوج ينتج I^A I^B (فصيلة AB)، و I^A i (فصيلة A)، و I^B i (فصيلة B)، و ii (فصيلة O) بنسبة 25% لكل منها.",
                    topic = "Complex"
                )
            )
        ),
        Lesson(
            id = "lesson_8",
            track = "Advanced",
            trackAr = "متقدم",
            titleEn = "Mutations & Genetic Variation",
            titleAr = "الطفرات والتنوع الجيني",
            durationMin = 13,
            iconName = "change_circle",
            summaryEn = "Investigate point mutations, frameshifts, and how mutations drive natural selection and biodiversity.",
            summaryAr = "تحقق من الطفرات النقطية، طفرات الإزاحة، وكيف تدفع الطفرات عجلة التطور والتنوع البيولوجي.",
            sections = listOf(
                LessonSection(
                    headingEn = "Molecular Anatomy of Mutations",
                    headingAr = "التشريح الجزيئي للطفرات",
                    contentEn = "Mutations are permanent alterations in the nucleotide sequence of DNA. Point mutations involve a single base pair change: silent (no amino acid change), missense (different amino acid), or nonsense (premature stop). Frameshifts insert or delete bases not in multiples of three.",
                    contentAr = "الطفرات هي تغيرات دائمة في تسلسل النيوكليوتيدات. تشمل الطفرات النقطية استبدال قاعدة واحدة: صامتة (لا تغير الحمض الأميني)، مؤثرة (تغير الحمض الأميني)، أو غير مجدية (توقف مبكر). بينما تؤدي طفرات الإدخال والحذف إلى إزاحة إطار القراءة بالكامل.",
                    keyTakeawayEn = "Not all mutations are harmful; mutations are the fundamental source of genetic diversity.",
                    keyTakeawayAr = "ليست كل الطفرات ضارة؛ الطفرات هي المصدر الأساسي للتنوع الوراثي والتكيف البيئي."
                )
            ),
            quickQuiz = listOf(
                QuizQuestion(
                    id = "q_l8_1",
                    questionEn = "What type of mutation occurs when a single nucleotide insertion alters the reading frame of all subsequent codons?",
                    questionAr = "ما نوع الطفرة التي تحدث عند إدخال نيوكليوتيد واحد مما يغير إطار القراءة لجميع الشفرات اللاحقة؟",
                    optionsEn = listOf("Frameshift mutation", "Silent mutation", "Missense mutation", "Inversion"),
                    optionsAr = listOf("طفرة إزاحة الإطار (Frameshift)", "طفرة صامتة", "طفرة مؤثرة", "طفرة انقلاب"),
                    correctIndex = 0,
                    explanationEn = "Because codons are read in non-overlapping triplets, inserting a single base alters every downstream codon.",
                    explanationAr = "نظراً لأن الشفرات تُقرأ في ثلاثيات، فإن إدخال قاعدة واحدة يغير كل الشفرات التالية وينتج بروتيناً غير فعال.",
                    topic = "Mutations"
                )
            )
        ),
        Lesson(
            id = "lesson_9",
            track = "Advanced",
            trackAr = "متقدم",
            titleEn = "Modern Biotechnology: CRISPR & PCR",
            titleAr = "التكنولوجيا الحيوية الحديثة: كريسبر وتفاعل البوليميراز المتسلسل PCR",
            durationMin = 15,
            iconName = "auto_awesome",
            summaryEn = "Understand revolutionary molecular biology tools including DNA amplification with PCR and targeted gene editing via CRISPR-Cas9.",
            summaryAr = "افهم أدوات البيولوجيا الجزيئية الثورية بما في ذلك تضخيم الحمض النووي بـ PCR وتعديل الجينات الموجه بتقنية كريسبر-كاس9.",
            sections = listOf(
                LessonSection(
                    headingEn = "CRISPR-Cas9 Precision Editing",
                    headingAr = "التعديل الجيني الدقيق بنظام كريسبر-كاس9",
                    contentEn = "Adapted from a bacterial immune defense system against phages, CRISPR-Cas9 uses a guide RNA (gRNA) complementary to a 20-base genomic target. The Cas9 endonuclease creates a precise double-stranded break, allowing targeted gene insertion, deletion, or correction.",
                    contentAr = "مستوحى من نظام المناعة البكتيري ضد الفيروسات، يستخدم كريسبر-كاس9 حمض نووي ريبوزي مرشد (gRNA) يوجه إنزيم Cas9 لقطع الـ DNA في موقع جيني دقيق للغاية لإصلاحه أو تعديله.",
                    keyTakeawayEn = "CRISPR enables targeted, molecular-level genomic rewriting.",
                    keyTakeawayAr = "يتيح كريسبر إعادة كتابة الجينات بدقة متناهية على المستوى الجزيئي."
                )
            ),
            quickQuiz = listOf(
                QuizQuestion(
                    id = "q_l9_1",
                    questionEn = "What molecular component guides the Cas9 enzyme to its specific target DNA sequence in CRISPR gene editing?",
                    questionAr = "ما هو المكون الجزيئي الذي يوجه إنزيم Cas9 إلى تسلسل الـ DNA المستهدف بدقة؟",
                    optionsEn = listOf("Guide RNA (gRNA)", "DNA Polymerase", "Transfer RNA (tRNA)", "Restriction endonuclease"),
                    optionsAr = listOf("الـ RNA المرشد (gRNA)", "إنزيم بلمرة الـ DNA", "الـ RNA الناقل (tRNA)", "إنزيم القطع المقيد"),
                    correctIndex = 0,
                    explanationEn = "The guide RNA contains a sequence complementary to the target DNA locus, directing Cas9 to cut at the exact site.",
                    explanationAr = "يحتوي الـ RNA المرشد على تسلسل مكمل للموقع المستهدف، مما يرشد إنزيم القطع Cas9 بدقة متناهية.",
                    topic = "Biotech"
                )
            )
        )
    )

    val GLOSSARY: List<GlossaryTerm> = listOf(
        GlossaryTerm(
            id = "t_allele",
            termEn = "Allele",
            termAr = "أليل (شكل بديل للجين)",
            category = "Classical",
            definitionEn = "One of two or more alternative forms of a gene that arise by mutation and are found at the same place on a chromosome.",
            definitionAr = "أحد الأشكال البديلة لجين معين يحدد صفة متباينة، ويقع في نفس الموقع على الكروموسوم المتماثل.",
            formulaOrSymbol = "A, a, I^A, I^B"
        ),
        GlossaryTerm(
            id = "t_dna",
            termEn = "DNA",
            termAr = "الحمض النووي (DNA)",
            category = "Molecular",
            definitionEn = "Deoxyribonucleic acid, a polymer composed of two polynucleotide chains that coil around each other in a double helix carrying genetic instructions.",
            definitionAr = "الحمض النووي الريبوزي منقوص الأكسجين؛ الجزيء الحيوي الحامل للشفرة الوراثية لجميع الكائنات الحية في صورة لولب مزدوج.",
            formulaOrSymbol = "Polynucleotide double helix"
        ),
        GlossaryTerm(
            id = "t_punnett",
            termEn = "Punnett Square",
            termAr = "مربع بانيت",
            category = "Classical",
            definitionEn = "A square diagram used to predict the genotypes and phenotypes of a particular cross or breeding experiment.",
            definitionAr = "مخطط شبكي ابتكره ريجينالد بانيت لحساب احتمالات الطرز الجينية والمظهرية الناتجة عن تزاوج وراثي.",
            formulaOrSymbol = "Grid n x n"
        ),
        GlossaryTerm(
            id = "t_mrna",
            termEn = "mRNA (Messenger RNA)",
            termAr = "الـ RNA الرسول (mRNA)",
            category = "Molecular",
            definitionEn = "A single-stranded RNA molecule that conveys genetic information from DNA to the ribosome for protein synthesis.",
            definitionAr = "جزيء حمض نووي أحادي السلسلة ينقل التعليمات الجينية من الـ DNA داخل النواة إلى الريبوسومات لتصنيع البروتين.",
            formulaOrSymbol = "Ribonucleic acid (A, U, C, G)"
        ),
        GlossaryTerm(
            id = "t_codon",
            termEn = "Codon",
            termAr = "الشفرة الثلاثية (الكودون)",
            category = "Molecular",
            definitionEn = "A sequence of three consecutive nucleotides that specifies a single amino acid or a termination signal during protein translation.",
            definitionAr = "تتابع من ثلاث قواعد نيتروجينية في الـ mRNA يرمز إلى حمض أميني معين أو شفرة توقف أثناء تصنيع البروتين.",
            formulaOrSymbol = "Triplet (e.g. AUG = Met)"
        ),
        GlossaryTerm(
            id = "t_heterozygous",
            termEn = "Heterozygous",
            termAr = "متباين الأليلات (هجين)",
            category = "Classical",
            definitionEn = "Having two different alleles of a particular gene or genes (e.g., Aa).",
            definitionAr = "امتلاك أليلين مختلفين لجين معين في الكائن الحي (مثل Aa).",
            formulaOrSymbol = "Aa"
        ),
        GlossaryTerm(
            id = "t_homozygous",
            termEn = "Homozygous",
            termAr = "متماثل الأليلات (نقي)",
            category = "Classical",
            definitionEn = "Having two identical alleles of a particular gene (e.g., AA or aa).",
            definitionAr = "امتلاك نسختين متطابقتين من أليل الجين، إما سائد نقي (AA) أو متنحٍ نقي (aa).",
            formulaOrSymbol = "AA or aa"
        ),
        GlossaryTerm(
            id = "t_crispr",
            termEn = "CRISPR-Cas9",
            termAr = "نظام كريسبر-كاس9",
            category = "Biotech",
            definitionEn = "A gene editing technology derived from bacterial antiviral defenses, capable of cutting and modifying DNA at precise sequences.",
            definitionAr = "تقنية تعديل جيني ثورية تتيح للعلماء قطع وإعادة كتابة تسلسلات الـ DNA بدقة وسرعة فائقة.",
            formulaOrSymbol = "gRNA + Cas9 nuclease"
        ),
        GlossaryTerm(
            id = "t_phenotype",
            termEn = "Phenotype",
            termAr = "الطراز المظهري",
            category = "Classical",
            definitionEn = "The observable physical properties of an organism, including appearance, development, and behavior.",
            definitionAr = "الخصائص والمواصفات الجسدية أو الوظيفية المرئية للكائن الحي، الناتجة عن تفاعل الطراز الجيني مع البيئة.",
            formulaOrSymbol = "Physical expression"
        ),
        GlossaryTerm(
            id = "t_genotype",
            termEn = "Genotype",
            termAr = "الطراز الجيني",
            category = "Classical",
            definitionEn = "The genetic constitution of an individual organism, specifically the alleles present at one or more gene loci.",
            definitionAr = "التركيب الوراثي الدقيق للكائن الحي ممثلاً في مجموعة الأليلات التي يحملها لجين معين.",
            formulaOrSymbol = "Genetic composition"
        ),
        GlossaryTerm(
            id = "t_meiosis",
            termEn = "Meiosis",
            termAr = "الانقسام المنصف (الميوزي)",
            category = "Cellular",
            definitionEn = "A specialized type of cell division that reduces the chromosome count by half, producing four genetically diverse haploid gametes.",
            definitionAr = "انقسام خلوي اختزالي يقلل عدد الكروموسومات إلى النصف لإنتاج الأمشاج (الحيوانات المنوية والبويضات) مع تحقيق التنوع الجيني.",
            formulaOrSymbol = "2n -> 4 x (1n)"
        ),
        GlossaryTerm(
            id = "t_mitosis",
            termEn = "Mitosis",
            termAr = "الانقسام المتساوي (الميتوزي)",
            category = "Cellular",
            definitionEn = "Cell division in which a eukaryotic somatic cell replicates its chromosomes and divides to produce two identical daughter cells.",
            definitionAr = "انقسام الخلية الجسدية لإنتاج خليتين ابنتين متطابقتين وراثياً للنمو وتجديد الأنسجة التالفة.",
            formulaOrSymbol = "2n -> 2 x (2n)"
        ),
        GlossaryTerm(
            id = "t_pcr",
            termEn = "PCR (Polymerase Chain Reaction)",
            termAr = "تفاعل البوليميراز المتسلسل (PCR)",
            category = "Biotech",
            definitionEn = "A laboratory technique used to rapidly produce millions to billions of copies of a specific segment of DNA.",
            definitionAr = "تقنية مخبرية حيوية تُستخدم لتضخيم ونسخ قطعة محددة من الـ DNA ملايين المرات في غضون ساعات قليلة.",
            formulaOrSymbol = "Thermal cycling amplification"
        ),
        GlossaryTerm(
            id = "t_chi_square",
            termEn = "Chi-Square Test (χ²)",
            termAr = "اختبار كاي تربيع (χ²)",
            category = "Classical",
            definitionEn = "A statistical goodness-of-fit test used in genetics to determine whether observed offspring numbers match theoretical Mendelian expectations.",
            definitionAr = "اختبار إحصائي يقيس مدى تطابق النتائج التجريبية المرصودة للأبناء مع النسب المتوقعة نظرياً وفق قوانين مندل.",
            formulaOrSymbol = "χ² = Σ((O - E)² / E)"
        )
    )

    val MUTATION_EXAMPLES: List<MutationExample> = listOf(
        MutationExample(
            id = "mut_silent",
            type = MutationType.SILENT_SUBSTITUTION,
            titleEn = "Silent Mutation (GAA -> GAG)",
            titleAr = "طفرة صامتة (GAA إلى GAG)",
            originalDna = "ATGGAACTG",
            mutatedDna = "ATGGAGCTG",
            mutatedPosition = 5,
            biologicalImpactEn = "The codon changes from GAA to GAG. Both codons code for Glutamic Acid (Glu). The polypeptide sequence remains identical, with no change in protein structure or enzymatic activity.",
            biologicalImpactAr = "تغير الكودون من GAA إلى GAG. كلا الكودونين يرمزان لنفس الحمض الأميني (حمض الجلوتاميك). يبقى تسلسل البروتين ووظيفته سليماً تماماً بفضل تكرار الشفرة الوراثية."
        ),
        MutationExample(
            id = "mut_sickle",
            type = MutationType.MISSENSE_SUBSTITUTION,
            titleEn = "Missense: Sickle Cell Mutation (GAG -> GTG)",
            titleAr = "طفرة استبدال مؤثرة: فقر الدم المنجلي (GAG إلى GTG)",
            originalDna = "ATGGTGCACCTGACTCCTGAGGAG",
            mutatedDna = "ATGGTGCACCTGACTCCTGTGGAG",
            mutatedPosition = 19,
            biologicalImpactEn = "At codon 6 of Beta-globin, GAG (Glutamate, hydrophilic) mutates to GTG (Valine, hydrophobic). This causes hemoglobin HbS molecules to polymerize into rigid fibers under low oxygen, distorting red blood cells into sickled shapes.",
            biologicalImpactAr = "في الكودون السادس لسلسلة بيتا-جلوبين، يتغير GAG (حمض الجلوتاميك المحب للماء) إلى GTG (فالين كاره للماء). يؤدي ذلك إلى تبلور الهيموجلوبين وتشوه كريات الدم الحمراء إلى شكل منجلي."
        ),
        MutationExample(
            id = "mut_nonsense",
            type = MutationType.NONSENSE_SUBSTITUTION,
            titleEn = "Nonsense Mutation: Premature STOP (AAG -> TAG)",
            titleAr = "طفرة غير مجدية: شفرة توقف مبكرة (AAG إلى TAG)",
            originalDna = "ATGAAATTCGGC",
            mutatedDna = "ATGTAGTTCGGC",
            mutatedPosition = 3,
            biologicalImpactEn = "Codon 2 mutates from AAG (Lysine) to TAG (UAG stop codon). Translation halts prematurely after only 1 amino acid, yielding a severely truncated, non-functional protein fragment.",
            biologicalImpactAr = "يتحول الكودون الثاني من AAG (لايسين) إلى TAG (شفرة توقف UAG). تتوقف عملية الترجمة فوراً وينتج بروتين قصير مبتور يفقد وظيفته الحيوية."
        ),
        MutationExample(
            id = "mut_frameshift_ins",
            type = MutationType.INSERTION_FRAMESHIFT,
            titleEn = "Insertion Frameshift (+C after Met)",
            titleAr = "طفرة إزاحة بالإدخال (+C بعد كودون البدء)",
            originalDna = "ATGTTTGGCCAA",
            mutatedDna = "ATGCTTTGGCCA",
            mutatedPosition = 3,
            biologicalImpactEn = "Inserting a single cytosine (C) shifts the entire downstream reading frame. Instead of Phe-Gly-Gln, the ribosome reads completely altered codons, destroying protein function.",
            biologicalImpactAr = "إدخال قاعدة سيتوسين واحدة يزيح إطار القراءة بالكامل لجميع الكودونات اللاحقة، مما يغير تسلسل الأحماض الأمينية كلياً وينتج بروتيناً غير صالح."
        )
    )

    val FULL_QUESTION_BANK: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = "qb_1",
            questionEn = "Which base in RNA replaces Thymine found in DNA?",
            questionAr = "ما هي القاعدة النيتروجينية في الـ RNA التي تحل محل الثايمين في الـ DNA؟",
            optionsEn = listOf("Uracil", "Adenine", "Guanine", "Cytosine"),
            optionsAr = listOf("يوراسيل (Uracil)", "أدينين", "جوانين", "سيتوسين"),
            correctIndex = 0,
            explanationEn = "RNA uses Uracil (U) instead of Thymine (T) to pair with Adenine (A).",
            explanationAr = "يحتوي الـ RNA على قاعدة اليوراسيل (U) بدلاً من الثايمين (T) وتتكامل مع الأدينين (A).",
            topic = "DNA"
        ),
        QuizQuestion(
            id = "qb_2",
            questionEn = "In a monohybrid cross between two heterozygous purple pea plants (Pp x Pp), what is the expected probability of white flower offspring (pp)?",
            questionAr = "في تزاوج أحادي بين نباتي بازلاء بنفسجيي الأزهار هجينين (Pp × Pp)، ما احتمال ظهور نباتات ذات أزهار بيضاء (pp)؟",
            optionsEn = listOf("25% (1/4)", "50% (1/2)", "75% (3/4)", "0%"),
            optionsAr = listOf("25% (1 من 4)", "50% (نصف)", "75% (3 من 4)", "0%"),
            correctIndex = 0,
            explanationEn = "The cross Pp x Pp yields 1 PP (25%), 2 Pp (50%), and 1 pp (25%).",
            explanationAr = "تزاوج Pp مع Pp ينتج 1 PP بنسبة 25%، و 2 Pp بنسبة 50%، و 1 pp بنسبة 25% بيضاء الأزهار.",
            topic = "Punnett"
        ),
        QuizQuestion(
            id = "qb_3",
            questionEn = "What bond connects complementary nitrogenous bases across the DNA double helix?",
            questionAr = "ما نوع الروابط التي تربط بين القواعد النيتروجينية المكملة في لولب الـ DNA المزدوج؟",
            optionsEn = listOf("Hydrogen bonds", "Covalent phosphodiester bonds", "Ionic bonds", "Peptide bonds"),
            optionsAr = listOf("روابط هيدروجينية", "روابط تساهمية فوسفاتية ثنائية الإستر", "روابط أيونية", "روابط ببتيدية"),
            correctIndex = 0,
            explanationEn = "Hydrogen bonds form between base pairs (2 between A-T, 3 between G-C), holding the two strands together.",
            explanationAr = "تتكون الروابط الهيدروجينية بين القواعد النيتروجينية (اثنتان بين A-T وثلاث بين G-C).",
            topic = "DNA"
        ),
        QuizQuestion(
            id = "qb_4",
            questionEn = "Which human blood type allele combination demonstrates codominance?",
            questionAr = "أي تركيبة من أليلات فصائل الدم لدى الإنسان تُظهر سيادة مشتركة؟",
            optionsEn = listOf("I^A and I^B producing Type AB", "I^A and i producing Type A", "ii producing Type O", "I^B and i producing Type B"),
            optionsAr = listOf("الأليلان I^A و I^B معاً لينتجا الفصيلة AB", "الأليل I^A مع i لينتجا الفصيلة A", "الأليلان ii لينتجا الفصيلة O", "الأليل I^B مع i لينتجا الفصيلة B"),
            correctIndex = 0,
            explanationEn = "Both I^A and I^B are codominant alleles, so both antigens A and B are produced simultaneously.",
            explanationAr = "الأليلان I^A و I^B كلاهما سائدان معاً، وبالتالي تظهر المستضدات A و B على سطح خلايا الدم الحمراء في الفصيلة AB.",
            topic = "Complex"
        ),
        QuizQuestion(
            id = "qb_5",
            questionEn = "What is the start codon in mRNA translation and which amino acid does it encode?",
            questionAr = "ما هو كودون البدء في ترجمة الـ mRNA وما هو الحمض الأميني الذي يحدده؟",
            optionsEn = listOf("AUG (Methionine)", "UAA (Stop)", "GGG (Glycine)", "CCC (Proline)"),
            optionsAr = listOf("AUG (الميثيونين)", "UAA (توقف)", "GGG (جلايسين)", "CCC (برولين)"),
            correctIndex = 0,
            explanationEn = "AUG is the universal start codon that initiates translation and specifies the amino acid Methionine.",
            explanationAr = "الكودون AUG هو كودون البدء العالمي الذي يستهل عملية الترجمة ويشفر الحمض الأميني ميثيونين.",
            topic = "DNA"
        ),
        QuizQuestion(
            id = "qb_6",
            questionEn = "Why does the Chi-Square test matter when analyzing real breeding experimental data?",
            questionAr = "لماذا يعتبر اختبار كاي تربيع مهماً عند تحليل نتائج تجارب التزاوج الوراثية الفعلية؟",
            optionsEn = listOf(
                "It tests whether observed deviations from expected ratios are due to chance",
                "It measures the length of DNA strands",
                "It calculates the rate of cell division",
                "It isolates specific alleles"
            ),
            optionsAr = listOf(
                "يختبر ما إذا كانت الفروق بين النتائج الفعلية والنسب المتوقعة ناتجة عن الصدفة العشوائية",
                "يقيس طول سلاسل الـ DNA",
                "يحسب معدل سرعة انقسام الخلايا",
                "يقوم بعزل الأليلات مخبرياً"
            ),
            correctIndex = 0,
            explanationEn = "Chi-Square assesses goodness-of-fit to determine if experimental counts statistically support Mendelian hypotheses.",
            explanationAr = "يقيس اختبار كاي تربيع حسن المطابقة للتأكد مما إذا كانت الفروق طفيفة بسبب الصدفة أم تشير لاختلاف بيولوجي حقيقي.",
            topic = "Punnett"
        )
    )
}
