package com.orient1caps2.orient1capstone2;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class quizActivity extends AppCompatActivity {
    private static final long TIME_PER_QUESTION = 30000;

    // UI Components
    private TextView questionText, timerText;
    private RadioGroup optionsGroup;
    private RadioButton optionTrue, optionFalse, optionA, optionB, optionC, optionD;
    private Button nextButton;
    private EditText answerInput;

    // Question Lists
    private List < Question > questions;
    private List < QuestionMed > mediumquestions;
    private List < QuestionHard > hardQuestions;

    // Game State
    private int currentIndex = 0;
    private int score = 0;
    private CountDownTimer countDownTimer;
    // Add these with other class variables
    private final List < Boolean > userEasyAnswers = new ArrayList < > ();
    private final List < Integer > userMediumAnswers = new ArrayList < > ();
    private final List < String > userHardAnswers = new ArrayList < > ();

    private boolean isReviewMode = false;
    private boolean isHardQuizFinished = false;

    @Override
    public void onBackPressed() {
        if (isReviewMode) {
            // Do nothing when in review mode
            return;
        }
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.exit_dialog, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(v -> {

            countDownTimer.cancel();

            dialog.dismiss();
            super.onBackPressed();

        });

        btnNo.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Get intent data
        String difficulty = getIntent().getStringExtra("difficulty");
        String quizCategory = getIntent().getStringExtra("quizCategory");

        // Set layout based on difficulty
        switch (difficulty) {
            case "Easy":
                setContentView(R.layout.activity_quiz_easy);
                initializeEasyQuiz(quizCategory);
                break;
            case "Medium":
                setContentView(R.layout.activity_quiz_medium);
                initializeMediumQuiz(quizCategory);
                break;
            case "Hard":
                setContentView(R.layout.activity_quiz_hard);
                initializeHardQuiz(quizCategory);
                break;
        }
    }

    private void initializeEasyQuiz(String quizCategory) {
        // Initialize UI components
        questionText = findViewById(R.id.questionText);
        optionsGroup = findViewById(R.id.optionsGroup);
        optionTrue = findViewById(R.id.optionTrue);
        optionFalse = findViewById(R.id.optionFalse);
        timerText = findViewById(R.id.timerText);
        nextButton = findViewById(R.id.nextButton);

        // Set header
        TextView title = findViewById(R.id.header);
        title.setText(quizCategory + " - Easy Mode");

        // Load questions based on category
        switch (quizCategory) {
            case "Dominican College of Tarlac Culture":
                loadQuestions();
                break;
            case "Study and Prayer Life":
                loadQuestionStudyandPrayer();
                break;
            case "Introduction on Student Life":
                loadQuestionIntroductionOnStudentLife();
                break;
            case "Education And Learning":
                loadQuestionEducationAndLearning();
                break;
            case "Importance of Education And Learning":
                loadQuestionImportanceEducationAndLearning();
                break;
        }

        Collections.shuffle(questions);
        showQuestion();



        nextButton.setOnClickListener(v -> {
            if (currentIndex < questions.size()) {
                evaluateAnswer();
                goToNextQuestion();
            } else {
                // Quiz is already finished, prevent further actions
                if (!isHardQuizFinished) {
                    finishQuiz();
                }
            }
        });
    }

    private void initializeMediumQuiz(String quizCategory) {
        // Initialize UI components
        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        optionsGroup = findViewById(R.id.optionsGroup);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        nextButton = findViewById(R.id.nextButton);

        // Set header
        TextView title = findViewById(R.id.header);
        title.setText(quizCategory + " - Medium Mode");

        // Load questions based on category
        switch (quizCategory) {
            case "Dominican College of Tarlac Culture":
                dctcultmedium();
                break;
            case "Study and Prayer Life":
                studandpraymedium();
                break;
            case "Introduction on Student Life":
                introductiononStudentLifeMed();
                break;
            case "Education And Learning":
                educationAndLearningmed();
                break;
            case "Importance of Education And Learning":
                impeducationAndLearningmed();
                break;
        }

        Collections.shuffle(mediumquestions);
        showMediumQuestion();

        nextButton.setOnClickListener(v -> {
            if (currentIndex < mediumquestions.size()) {
                evaluateMediumAnswer();
                goToNextMediumQuestion();
            } else {
                // Quiz is already finished, prevent further actions
                if (!isHardQuizFinished) {
                    finishMediumQuiz();
                }
            }
        });
    }

    private void initializeHardQuiz(String quizCategory) {
        // Initialize UI components
        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        answerInput = findViewById(R.id.answerInput);
        nextButton = findViewById(R.id.nextButton);

        // Set header
        TextView title = findViewById(R.id.header);
        title.setText(quizCategory + " - Hard Mode");

        // Load questions based on category
        switch (quizCategory) {
            case "Dominican College of Tarlac Culture":
                dctcultHard();
                break;
            case "Study and Prayer Life":
                studandprayHard();
                break;
            case "Introduction on Student Life":
                introinstudentlifeHard();
                break;
            case "Education And Learning":
                EducationandHard();
                break;
            case "Importance of Education And Learning":
                impEducationandHard();
                break;
        }

        Collections.shuffle(hardQuestions);
        showHardQuestion();

        nextButton.setOnClickListener(v -> {
            if (currentIndex < hardQuestions.size()) {
                evaluateHardAnswer();
                goToNextHardQuestion();
            } else {
                // Quiz is already finished, prevent further actions
                if (!isHardQuizFinished) {
                    finishHardQuiz();
                }
            }
        });
    }

    // Question loading methods
    public void loadQuestions() {
        questions = new ArrayList < > ();

        questions.add(new Question("San Nicolas Academy was founded in 1939 by Fr. Mariano V. Sablay, O.P.", true));
        questions.add(new Question("Dominican School was transferred to its present site in 1960.", true));
        questions.add(new Question("Dominican College of Tarlac is run by the Dominican Sisters of St. Catherine of Siena.", false)); // Actually run by Dominican Order of Preachers - O.P.
        questions.add(new Question("Gradualism in education means the school offers programs from preschool to college.", true));
        questions.add(new Question("The Dominican Order is also known as the Order of Preachers.", true));
        questions.add(new Question("The DCT logo emphasizes faith in God and love for humanity.", true));
        questions.add(new Question("The motto in the DCT logo is “In the Service of Truth.”", false)); // Actually “Pro Deo et Patria” and “Caritas”
        questions.add(new Question("The Order of Preachers was founded by St. Dominic de Guzman.", true));
        questions.add(new Question("Sr. Ma. Asuncion M. Manalang, O.P. is one of the school administrators.", true));
        questions.add(new Question("The philosophy of education of DCT is focused on forming responsible and Christ-centered individuals.", true));
        questions.add(new Question("The objectives of DCT include helping students become academically competent and socially responsible.", true));
        questions.add(new Question("The Bachelor of Arts and Computer Secretarial courses were introduced in 1980.", true));
        questions.add(new Question("The Bachelor of Elementary Education program started in 1997.", true));
        questions.add(new Question("In 2009, DCT opened the Bachelor of Science in Criminology program.", false)); // Introduced in 2015, 2009 was BS IT
        questions.add(new Question("The Bachelor of Science in Business Administration program was first offered in 2011.", true));
    }

    public void loadQuestionStudyandPrayer() {
        questions = new ArrayList < > ();

        questions.add(new Question("St. Dominic De Guzman was born in Caleruega, Spain in 1170.", true));
        questions.add(new Question("The parents of St. Dominic were Felix De Guzman and Juana De Aza.", true));
        questions.add(new Question("St. Dominic had two siblings named Anthony and Mnemos.", false)); // Incorrect: it's Mannes
        questions.add(new Question("At the age of 14, St. Dominic studied Theology at the University of Palencia.", true));
        questions.add(new Question("During a famine, St. Dominic sold his precious books to feed the poor.", true));
        questions.add(new Question("The TRIVIUM consisted of Grammar, Rhetoric, and Logic.", true));
        questions.add(new Question("The QUADRIVIUM included Arithmetic, Geometry, Music, and Astronomy.", true));
        questions.add(new Question("While traveling to Denmark with Bishop Diego in 1203, St. Dominic encountered the Albigensian heresy in southern France.", true));
        questions.add(new Question("In 1206, St. Dominic formed a community of monks at Prouille.", false)); // It was nuns
        questions.add(new Question("The Order of Preachers (Dominicans) was officially approved by Pope Honorius III in 1216.", true));
        questions.add(new Question("St. Dominic died on August 6, 1231, at the age of 60.", false)); // He died in 1221 at 51
        questions.add(new Question("The Dominican College of Tarlac believes that education should lead to union with God, community with others, and harmony with creation.", true));
        questions.add(new Question("The Dominican learning system nurtures the human person through opportunities for reason, faith, and appreciation of values.", true));
        questions.add(new Question("The Dominicans continue St. Dominic’s mission of study, prayer, and preaching up to the present time.", true));
        questions.add(new Question("Scholastic (Thomistic) Philosophy emphasizes the rejection of faith in favor of pure reason.", false)); // It seeks harmony of faith and reason
    }

    public void loadQuestionIntroductionOnStudentLife() {
        questions = new ArrayList<>();

        questions.add(new Question("Student life is considered the golden period of development.", true));
        questions.add(new Question("Managing time means ignoring schoolwork and focusing on hobbies only.", false));
        questions.add(new Question("College and school life are opportunities for personal growth.", true));
        questions.add(new Question("Financial constraints mean having plenty of money for education.", false));
        questions.add(new Question("Health issues can affect a student’s learning.", true));
        questions.add(new Question("Mental health issues are not part of student challenges.", false));
        questions.add(new Question("Social issues involve problems with friends, peers, or the community.", true));
        questions.add(new Question("Technological barriers include lack of gadgets and internet access.", true));
        questions.add(new Question("Lack of motivation makes students eager to study.", false));
        questions.add(new Question("Family problems can cause stress and affect student performance.", true));
        questions.add(new Question("A good student always puts studies as their top priority.", true));
        questions.add(new Question("SMART goals are vague and not measurable.", false));
        questions.add(new Question("Participating in classroom activities makes students passive learners.", false));
        questions.add(new Question("Paying attention to teachers is necessary to become successful in life.", true));
        questions.add(new Question("Studying in a group helps reinforce learning through discussion and explanation.", true));
    }

    public void loadQuestionEducationAndLearning() {
        questions = new ArrayList < > ();

        questions.add(new Question("Education is the process of acquiring new knowledge, skills, values, and habits.", false)); // Education is about imparting
        questions.add(new Question("Learning is the process of acquiring or modifying existing knowledge, skills, and behaviors.", true));
        questions.add(new Question("Formal education happens outside schools and has no structured curriculum.", false));
        questions.add(new Question("Informal education often happens naturally through daily life experiences.", true));
        questions.add(new Question("Non-formal education includes workshops, community programs, and vocational training.", true));
        questions.add(new Question("People with visual-spatial intelligence are good at visualizing things and understanding maps, images, and patterns.", true));
        questions.add(new Question("Interpersonal intelligence means being more in tune with nature and caring for the environment.", false)); // That's naturalist
        questions.add(new Question("Intrapersonal intelligence is about being aware of one’s own emotions, feelings, and motivations.", true));
        questions.add(new Question("Musical intelligence refers to the ability to think in rhythms, sounds, and patterns.", true));
        questions.add(new Question("People with strong logical-mathematical intelligence are skilled in reasoning and problem solving.", true));
        questions.add(new Question("Bodily-kinesthetic intelligence is the ability to move objects with the mind instead of physical effort.", false)); // Misconception
        questions.add(new Question("Verbal-linguistic intelligence refers to people who are good at using words in both speaking and writing.", true));
        questions.add(new Question("Naturalist intelligence means being able to connect deeply with nature and explore the environment.", true));
        questions.add(new Question("Existential intelligence deals with asking deeper questions about life and human existence.", true));
        questions.add(new Question("Education is not important in improving lives or giving meaning to the world around us.", false)); // Incorrect view
    }

    public void loadQuestionImportanceEducationAndLearning() {
        questions = new ArrayList < > ();

        // From your quiz (Education and Learning)
        questions.add(new Question("Education is the process of facilitating learning and acquiring knowledge, skills, values, beliefs, and habits.", true));
        questions.add(new Question("Learning means only memorizing facts without changing behavior or skills.", false));
        questions.add(new Question("One of the primary goals of education is to impart knowledge about the world.", true));
        questions.add(new Question("Imparting skills equips individuals with practical abilities for tasks and problem-solving.", true));
        questions.add(new Question("The vision emphasizes creating a God-loving community of servant leaders with truth and compassion.", true));

        // From your quiz (Stress and Stressors)
        questions.add(new Question("A stressor is anything that causes stress, either physical, emotional, or psychological.", true));
        questions.add(new Question("Positive stress (eustress) can motivate people and lead to personal growth and achievement.", true));
        questions.add(new Question("Negative stress (distress) always helps people perform better in every situation.", false));
        questions.add(new Question("Permanent stress comes from ongoing problems in daily life, such as financial difficulties.", true));
        questions.add(new Question("Academic stress can arise from exams, deadlines, and workload at school or university.", true));
        questions.add(new Question("Professional stress is only experienced by doctors and nurses.", false));
        questions.add(new Question("Environmental stressors include pollution, noise, disasters, and unsafe surroundings.", true));
        questions.add(new Question("The “fight-or-flight” response is the body’s natural reaction to stress, preparing a person to face danger or escape.", true));
        questions.add(new Question("Stress only affects emotions and never causes physical symptoms.", false));
        questions.add(new Question("Symptoms of stress may include emotional changes, physical symptoms, and behavioral effects.", true));
    }

    // Medium question loading methods
    private void dctcultmedium() {
        mediumquestions = new ArrayList < > ();

        mediumquestions.add(new QuestionMed("Who founded San Nicolas Academy in 1939?",
                new String[] {
                        "Fr. Juan de Salcedo",
                        "Fr. Mariano V. Saddy, O.P.",
                        "St. Dominic de Guzman",
                        "Sr. Ma. Asuncion M. Manalang, O.P."
                }, 1));
        mediumquestions.add(new QuestionMed("In what year was the Dominican School transferred to its present site?",
                new String[] {
                        "1950",
                        "1960",
                        "1970",
                        "1980"
                }, 1));
        mediumquestions.add(new QuestionMed("What religious order manages Dominican College of Tarlac?",
                new String[] {
                        "Augustinian Order",
                        "Benedictine Order",
                        "Dominican Order of Preachers (O.P.)",
                        "Jesuit Order"
                }, 2));
        mediumquestions.add(new QuestionMed("What does Gradualism in Education mean in DCT?",
                new String[] {
                        "Learning only at the college level",
                        "Step-by-step educational offerings from preschool to college",
                        "Focus on extracurricular activities",
                        "Online learning programs only"
                }, 1));
        mediumquestions.add(new QuestionMed("The Dominican Order is also called:",
                new String[] {
                        "The Order of Teachers",
                        "The Order of Preachers",
                        "The Order of Faith",
                        "The Order of Saints"
                }, 1));
        mediumquestions.add(new QuestionMed("What two values are emphasized in the DCT logo?",
                new String[] {
                        "Wealth and Prosperity",
                        "Faith in God and Love for Humanity",
                        "Power and Justice",
                        "Education and Discipline"
                }, 1));
        mediumquestions.add(new QuestionMed("What is written in the DCT logo?",
                new String[] {
                        "“Truth Shall Prevail”",
                        "“Caritas – Uniform Love” and “Pro Deo et Patria”",
                        "“Excellence for All”",
                        "“Service and Honor”"
                }, 1));
        mediumquestions.add(new QuestionMed("Who founded the Order of Preachers?",
                new String[] {
                        "St. Francis of Assisi",
                        "St. Augustine",
                        "St. Dominic de Guzman",
                        "St. Ignatius of Loyola"
                }, 2));
        mediumquestions.add(new QuestionMed("Who among the sisters is mentioned as part of DCT’s leadership?",
                new String[] {
                        "Sr. Ma. Lourdes O.P.",
                        "Sr. Ma. Asuncion M. Manalang, O.P.",
                        "Sr. Angela Gonzales, O.P.",
                        "Sr. Clara Villanueva, O.P."
                }, 1));
        mediumquestions.add(new QuestionMed("The philosophy of DCT education focuses on:",
                new String[] {
                        "Training athletes only",
                        "Forming responsible and Christ-centered individuals",
                        "Political leadership",
                        "Economic development"
                }, 1));
        mediumquestions.add(new QuestionMed("Which is NOT part of DCT objectives?",
                new String[] {
                        "Academic excellence",
                        "Social responsibility",
                        "Formation of criminals",
                        "Christ-centered values"
                }, 2));
        mediumquestions.add(new QuestionMed("When were Bachelor of Arts and Computer Secretarial courses first offered?",
                new String[] {
                        "1970",
                        "1980",
                        "1990",
                        "2000"
                }, 1));
        mediumquestions.add(new QuestionMed("In what year did the Bachelor of Elementary Education program start?",
                new String[] {
                        "1985",
                        "1990",
                        "1997",
                        "2005"
                }, 2));
        mediumquestions.add(new QuestionMed("What program was introduced in 2009?",
                new String[] {
                        "Bachelor of Science in Criminology",
                        "Bachelor of Science in Information Technology",
                        "Bachelor of Science in Business Administration",
                        "TESDA Restaurant Management"
                }, 1));
        mediumquestions.add(new QuestionMed("In what year did the Bachelor of Science in Criminology begin at DCT?",
                new String[] {
                        "2009",
                        "2010",
                        "2015",
                        "2020"
                }, 2));
    }

    //Study and Prayer Life
    private void studandpraymedium() {
        mediumquestions = new ArrayList < > ();

        mediumquestions.add(new QuestionMed("Where was St. Dominic De Guzman born?",
                new String[] {
                        "Madrid, Spain",
                        "Caleruega, Spain",
                        "Palencia, Spain",
                        "Leon, Spain"
                }, 1));

        mediumquestions.add(new QuestionMed("Who were the parents of St. Dominic?",
                new String[] {
                        "Diego De Guzman and Teresa De Aza",
                        "Felix De Guzman and Juana De Aza",
                        "Pedro De Guzman and Maria De Aza",
                        "Anthony De Guzman and Clara De Aza"
                }, 1));

        mediumquestions.add(new QuestionMed("Which of the following were siblings of St. Dominic?",
                new String[] {
                        "Anthony and Mannes",
                        "Peter and Martin",
                        "Diego and Francis",
                        "Thomas and Augustine"
                }, 0));

        mediumquestions.add(new QuestionMed("At what age did St. Dominic study Theology at the University of Palencia?",
                new String[] {
                        "10",
                        "12",
                        "14",
                        "16"
                }, 2));

        mediumquestions.add(new QuestionMed("What did St. Dominic do during a famine to help the poor?",
                new String[] {
                        "Sold his house",
                        "Sold his clothes",
                        "Sold his books",
                        "Sold his crops"
                }, 2));

        mediumquestions.add(new QuestionMed("Which subjects are included in the TRIVIUM?",
                new String[] {
                        "Grammar, Logic, Rhetoric",
                        "Arithmetic, Geometry, Music",
                        "Theology, Philosophy, History",
                        "Science, Art, Literature"
                }, 0));

        mediumquestions.add(new QuestionMed("Which subjects are included in the QUADRIVIUM?",
                new String[] {
                        "Theology, Philosophy, Ethics, Law",
                        "Arithmetic, Geometry, Music, Astronomy",
                        "Grammar, Rhetoric, Logic",
                        "History, Literature, Arts, Medicine"
                }, 1));

        mediumquestions.add(new QuestionMed("In which year did St. Dominic travel with Bishop Diego to Denmark?",
                new String[] {
                        "1195",
                        "1203",
                        "1215",
                        "1221"
                }, 1));

        mediumquestions.add(new QuestionMed("Whom did St. Dominic form into a community at Prouille in 1206?",
                new String[] {
                        "Priests",
                        "Monks",
                        "Nuns",
                        "Hermits"
                }, 2));

        mediumquestions.add(new QuestionMed("Who officially approved the Order of Preachers in 1216?",
                new String[] {
                        "Pope Innocent III",
                        "Pope Gregory IX",
                        "Pope Honorius III",
                        "Pope Urban II"
                }, 2));

        mediumquestions.add(new QuestionMed("When did St. Dominic die?",
                new String[] {
                        "August 6, 1216",
                        "August 6, 1221",
                        "August 6, 1225",
                        "August 6, 1230"
                }, 1));

        mediumquestions.add(new QuestionMed("At what age did St. Dominic die?",
                new String[] {
                        "40",
                        "45",
                        "51",
                        "60"
                }, 2));

        mediumquestions.add(new QuestionMed("Which of the following is a goal of education according to Dominican College of Tarlac?",
                new String[] {
                        "Wealth and success",
                        "Union with God",
                        "Political influence",
                        "Worldly achievements"
                }, 1));

        mediumquestions.add(new QuestionMed("According to DCT’s philosophy, education should also promote:",
                new String[] {
                        "Competition and rivalry",
                        "Community with others and harmony with creation",
                        "Authority and obedience only",
                        "Innovation without values"
                }, 1));

        mediumquestions.add(new QuestionMed("Scholastic (Thomistic) philosophy emphasizes:",
                new String[] {
                        "Faith alone without reason",
                        "Reason alone without faith",
                        "Harmony of faith and reason",
                        "Rejection of philosophy"
                }, 2));
    }

    private void introductiononStudentLifeMed() {
        mediumquestions = new ArrayList<>();

        mediumquestions.add(new QuestionMed("What is considered the golden period of development in a person’s life?",
                new String[]{
                        "Childhood",
                        "Student life",
                        "Adulthood",
                        "Retirement"
                }, 1));

        mediumquestions.add(new QuestionMed("Which of the following is the main academic task of students?",
                new String[]{
                        "Managing money",
                        "Going to classes and learning",
                        "Socializing only",
                        "Traveling"
                }, 1));

        mediumquestions.add(new QuestionMed("Balancing schoolwork with hobbies, sports, and part-time jobs refers to:",
                new String[]{
                        "Social issues",
                        "Managing time",
                        "Motivation",
                        "Personal growth"
                }, 1));

        mediumquestions.add(new QuestionMed("College or school life is about discovering yourself, meeting new people, and learning life skills. This is called:",
                new String[]{
                        "Academic pressure",
                        "Personal growth",
                        "Financial constraint",
                        "Time management"
                }, 1));

        mediumquestions.add(new QuestionMed("Which of the following is an example of a challenge students face?",
                new String[]{
                        "Health issues",
                        "Time management",
                        "Lack of motivation",
                        "All of the above"
                }, 3));

        mediumquestions.add(new QuestionMed("Setting SMART goals helps students:",
                new String[]{
                        "Waste time",
                        "Achieve what they want in life",
                        "Avoid responsibility",
                        "Forget studies"
                }, 1));

        mediumquestions.add(new QuestionMed("What is the first priority of a good student?",
                new String[]{
                        "Social life",
                        "Studies",
                        "Traveling",
                        "Leisure"
                }, 1));

        mediumquestions.add(new QuestionMed("Paying attention in class is necessary because:",
                new String[]{
                        "It helps you become successful in life",
                        "Teachers are strict",
                        "Students like it",
                        "It avoids punishment"
                }, 0));

        mediumquestions.add(new QuestionMed("Which skill enables students to do more and better work in less time?",
                new String[]{
                        "Leadership",
                        "Time management",
                        "Motivation",
                        "Social skills"
                }, 1));

        mediumquestions.add(new QuestionMed("A student’s involvement in classroom and school activities makes him/her:",
                new String[]{
                        "A passive learner",
                        "An active member",
                        "A lazy student",
                        "A leader only"
                }, 1));

        mediumquestions.add(new QuestionMed("Which of the following is NOT a common student issue?",
                new String[]{
                        "Family problems",
                        "Financial constraints",
                        "Job promotions",
                        "Social issues"
                }, 2));

        mediumquestions.add(new QuestionMed("What does 'student life in school' mainly help with?",
                new String[]{
                        "Learning manners, discipline, and knowledge",
                        "Skipping classes",
                        "Playing games only",
                        "Avoiding responsibilities"
                }, 0));

        mediumquestions.add(new QuestionMed("Studying with peers helps students by:",
                new String[]{
                        "Wasting time",
                        "Reinforcing learning through discussion",
                        "Competing only",
                        "Avoiding homework"
                }, 1));

        mediumquestions.add(new QuestionMed("Which among these is a technological barrier?",
                new String[]{
                        "Lack of gadgets and internet",
                        "Lack of sleep",
                        "Family arguments",
                        "Peer pressure"
                }, 0));

        mediumquestions.add(new QuestionMed("Which is a personal issue that might affect studies?",
                new String[]{
                        "Time management",
                        "Family problems",
                        "Health issues",
                        "All of the above"
                }, 3));
    }

    private void educationAndLearningmed() {
        mediumquestions = new ArrayList < > ();

        mediumquestions.add(new QuestionMed("What is the process of imparting knowledge, skills, values, and habits to others?",
                new String[] {
                        "Learning",
                        "Education",
                        "Training",
                        "Informal knowledge"
                }, 1));

        mediumquestions.add(new QuestionMed("Which of the following best defines learning?",
                new String[] {
                        "Teaching structured lessons",
                        "Acquiring or modifying knowledge, skills, and behaviors",
                        "Conducting assessments",
                        "Giving instructions"
                }, 1));

        mediumquestions.add(new QuestionMed("Which type of education follows a structured curriculum, certified teachers, and formal assessments?",
                new String[] {
                        "Non-formal Education",
                        "Informal Education",
                        "Formal Education",
                        "Experiential Education"
                }, 2));

        mediumquestions.add(new QuestionMed("When students learn through daily life experiences without a formal curriculum, it is called:",
                new String[] {
                        "Informal Education",
                        "Formal Education",
                        "Non-formal Education",
                        "Professional Training"
                }, 0));

        mediumquestions.add(new QuestionMed("Workshops, community-based programs, and vocational training are examples of:",
                new String[] {
                        "Formal Education",
                        "Informal Education",
                        "Non-formal Education",
                        "Naturalist Learning"
                }, 2));

        mediumquestions.add(new QuestionMed("Which intelligence is about being aware of your own emotions, feelings, and motivations?",
                new String[] {
                        "Interpersonal",
                        "Intrapersonal",
                        "Logical-Mathematical",
                        "Spatial"
                }, 1));

        mediumquestions.add(new QuestionMed("A person who loves exploring nature, animals, and the environment has strong:",
                new String[] {
                        "Interpersonal Intelligence",
                        "Naturalist Intelligence",
                        "Musical Intelligence",
                        "Physical Intelligence"
                }, 1));

        mediumquestions.add(new QuestionMed("Who is most likely strong in verbal-linguistic intelligence?",
                new String[] {
                        "Someone who can solve math equations quickly",
                        "Someone good at using words in writing and speaking",
                        "Someone who enjoys gardening",
                        "Someone who remembers faces easily"
                }, 1));

        mediumquestions.add(new QuestionMed("A teacher who can explain lessons clearly and write creatively shows which intelligence?",
                new String[] {
                        "Bodily-Kinesthetic",
                        "Logical-Mathematical",
                        "Verbal-Linguistic",
                        "Musical"
                }, 2));

        mediumquestions.add(new QuestionMed("People who are good at understanding and interacting with others show:",
                new String[] {
                        "Intrapersonal Intelligence",
                        "Interpersonal Intelligence",
                        "Naturalist Intelligence",
                        "Logical Intelligence"
                }, 1));

        mediumquestions.add(new QuestionMed("Which intelligence is about reasoning, recognizing patterns, and solving problems?",
                new String[] {
                        "Logical-Mathematical",
                        "Bodily-Kinesthetic",
                        "Spatial",
                        "Musical"
                }, 0));

        mediumquestions.add(new QuestionMed("A dancer who has great body control and coordination demonstrates:",
                new String[] {
                        "Naturalist Intelligence",
                        "Intrapersonal Intelligence",
                        "Bodily-Kinesthetic Intelligence",
                        "Verbal-Linguistic"
                }, 2));

        mediumquestions.add(new QuestionMed("People who are strong in musical intelligence usually:",
                new String[] {
                        "Think in patterns, rhythms, and sounds",
                        "Prefer solving logical equations",
                        "Enjoy nature and animals",
                        "Work well in group activities"
                }, 0));

        mediumquestions.add(new QuestionMed("Which intelligence focuses on asking deep questions about life and existence?",
                new String[] {
                        "Existential Intelligence",
                        "Social Intelligence",
                        "Naturalist Intelligence",
                        "Logical-Mathematical"
                }, 0));

        mediumquestions.add(new QuestionMed("Which intelligence is about being good at visualizing objects, images, and spatial understanding?",
                new String[] {
                        "Verbal-Linguistic",
                        "Visual-Spatial",
                        "Logical-Mathematical",
                        "Musical"
                }, 1));
    }

    private void impeducationAndLearningmed() {
        mediumquestions = new ArrayList < > ();

        mediumquestions.add(new QuestionMed("What is the process of facilitating learning and acquiring knowledge, skills, values, beliefs, and habits?",
                new String[] {
                        "Teaching",
                        "Education",
                        "Training",
                        "Memorization"
                }, 1));

        mediumquestions.add(new QuestionMed("Which of the following BEST defines learning?",
                new String[] {
                        "Simply storing information",
                        "Acquiring and modifying knowledge, skills, and behaviors",
                        "Copying others’ actions",
                        "Forgetting old habits"
                }, 1));

        mediumquestions.add(new QuestionMed("One of the primary goals of education is:",
                new String[] {
                        "To impart knowledge",
                        "To avoid learning",
                        "To memorize facts only",
                        "To ignore values"
                }, 0));

        mediumquestions.add(new QuestionMed("What does “imparting skills” in education mean?",
                new String[] {
                        "Memorizing lessons",
                        "Shaping beliefs",
                        "Providing practical abilities for tasks and problem-solving",
                        "Avoiding challenges"
                }, 2));

        mediumquestions.add(new QuestionMed("According to the vision, education should build:",
                new String[] {
                        "A rich society",
                        "A God-loving community with servant leaders and compassion",
                        "A competitive business environment",
                        "A stress-free community"
                }, 1));

        mediumquestions.add(new QuestionMed("What is a stressor?",
                new String[] {
                        "A relaxing activity",
                        "Something that causes stress",
                        "A type of exercise",
                        "A positive attitude"
                }, 1));

        mediumquestions.add(new QuestionMed("Which type of stress motivates people and leads to growth?",
                new String[] {
                        "Chronic stress",
                        "Distress",
                        "Eustress",
                        "Environmental stress"
                }, 2));

        mediumquestions.add(new QuestionMed("Negative stress that harms performance and health is called:",
                new String[] {
                        "Eustress",
                        "Relaxation",
                        "Distress",
                        "Motivation"
                }, 2));

        mediumquestions.add(new QuestionMed("Which of the following is an example of permanent stress?",
                new String[] {
                        "Loud noise for a few minutes",
                        "Traffic jam for an hour",
                        "Long-term financial difficulties",
                        "A sudden surprise quiz"
                }, 2));

        mediumquestions.add(new QuestionMed("Academic stress may be caused by:",
                new String[] {
                        "Watching TV",
                        "Exams and deadlines",
                        "Relaxing with friends",
                        "Sleeping"
                }, 1));

        mediumquestions.add(new QuestionMed("Which statement about professional stress is TRUE?",
                new String[] {
                        "Only teachers experience it",
                        "It can affect workers in many jobs",
                        "It only occurs in hospitals",
                        "It is always positive"
                }, 1));

        mediumquestions.add(new QuestionMed("Which of the following is an environmental stressor?",
                new String[] {
                        "Meditation",
                        "Pollution",
                        "Good grades",
                        "Prayer"
                }, 1));

        mediumquestions.add(new QuestionMed("The body’s natural “fight-or-flight” response prepares a person to:",
                new String[] {
                        "Sleep deeply",
                        "Relax instantly",
                        "Face danger or escape",
                        "Avoid exercise"
                }, 2));

        mediumquestions.add(new QuestionMed("Stress affects:",
                new String[] {
                        "Only emotions",
                        "Only physical health",
                        "Only behavior",
                        "Emotions, body, and behavior"
                }, 3));

        mediumquestions.add(new QuestionMed("Which of the following is NOT a common symptom of stress?",
                new String[] {
                        "Headaches",
                        "Fatigue",
                        "Improved relaxation",
                        "Stomach pain"
                }, 2));
    }

    // Hard question loading methods
    private void dctcultHard() {
        hardQuestions = new ArrayList < > ();

        hardQuestions.add(new QuestionHard("Who founded San Nicolas Academy?", "Fr. Mariano V. Saddy, O.P."));
        hardQuestions.add(new QuestionHard("What year was San Nicolas Academy founded?", "1939"));
        hardQuestions.add(new QuestionHard("In what year was the Dominican School transferred to its present site?", "1960"));
        hardQuestions.add(new QuestionHard("What religious order manages Dominican College of Tarlac?", "Dominican Order of Preachers (O.P.)"));
        hardQuestions.add(new QuestionHard("What term describes DCT’s step-by-step program offerings from preschool to college?", "Gradualism in Education"));
        hardQuestions.add(new QuestionHard("Another name for the Dominican Order is?", "Order of Preachers"));
        hardQuestions.add(new QuestionHard("What two values are emphasized in the DCT logo?", "Faith in God and Love for Humanity"));
        hardQuestions.add(new QuestionHard("What Latin phrase in the DCT logo means 'For God and Country'?", "Pro Deo et Patria"));
        hardQuestions.add(new QuestionHard("Who founded the Order of Preachers?", "St. Dominic de Guzman"));
        hardQuestions.add(new QuestionHard("Name a sister mentioned as part of the leadership of DCT.", "Sr. Ma. Asuncion M. Manalang, O.P."));
        hardQuestions.add(new QuestionHard("DCT’s philosophy focuses on forming ________ and ________ individuals.", "Responsible and Christ-centered"));
        hardQuestions.add(new QuestionHard("One objective of DCT is to help students become academically ________.", "Competent"));
        hardQuestions.add(new QuestionHard("In what year were Bachelor of Arts and Computer Secretarial courses first offered?", "1980"));
        hardQuestions.add(new QuestionHard("When was the Bachelor of Elementary Education program introduced?", "1997"));
        hardQuestions.add(new QuestionHard("Which academic program was introduced in 2009?", "Bachelor of Science in Information Technology"));
        hardQuestions.add(new QuestionHard("In what year was the Bachelor of Science in Criminology program added?", "2015"));
    }

    private void studandprayHard() {
        hardQuestions = new ArrayList < > ();

        hardQuestions.add(new QuestionHard("He was born in Caleruega, Spain in 1170 and later founded the Order of Preachers.", "St. Dominic De Guzman"));
        hardQuestions.add(new QuestionHard("The father of St. Dominic.", "Felix De Guzman"));
        hardQuestions.add(new QuestionHard("The mother of St. Dominic.", "Juana De Aza"));
        hardQuestions.add(new QuestionHard("The two siblings of St. Dominic.", "Anthony and Mannes"));
        hardQuestions.add(new QuestionHard("The school where St. Dominic studied Theology at age 14.", "University of Palencia"));
        hardQuestions.add(new QuestionHard("The kingdom where the University of Palencia was located.", "Kingdom of Leon"));
        hardQuestions.add(new QuestionHard("During a terrible famine, St. Dominic sold these to help the poor.", "His books"));
        hardQuestions.add(new QuestionHard("The three subjects included in the Trivium.", "Grammar, Rhetoric, Logic"));
        hardQuestions.add(new QuestionHard("The four subjects included in the Quadrivium.", "Arithmetic, Geometry, Music, Astronomy"));
        hardQuestions.add(new QuestionHard("In 1203, St. Dominic traveled with this bishop to Denmark.", "Bishop Diego"));
        hardQuestions.add(new QuestionHard("The heresy St. Dominic encountered in southern France.", "Albigensian heresy"));
        hardQuestions.add(new QuestionHard("The community St. Dominic founded in Prouille in 1206.", "Community of nuns"));
        hardQuestions.add(new QuestionHard("The Pope who approved the Order of Preachers in 1216.", "Pope Honorius III"));
        hardQuestions.add(new QuestionHard("The date of St. Dominic’s death.", "August 6, 1221"));
        hardQuestions.add(new QuestionHard("The philosophy that emphasizes harmony between faith and reason, adopted by the Dominican tradition.", "Scholastic (Thomistic) Philosophy"));
    }

    private void introinstudentlifeHard() {
        hardQuestions = new ArrayList<>();

        hardQuestions.add(new QuestionHard(
                "The stage in life considered as the golden period of development.",
                "Student life"));

        hardQuestions.add(new QuestionHard(
                "The main academic task of students.",
                "Learning"));

        hardQuestions.add(new QuestionHard(
                "Balancing schoolwork with hobbies and other activities.",
                "Managing time"));

        hardQuestions.add(new QuestionHard(
                "Discovering your passion and meeting new people.",
                "Personal growth"));

        hardQuestions.add(new QuestionHard(
                "Problems related to the body that may affect school performance.",
                "Health issues"));

        hardQuestions.add(new QuestionHard(
                "Difficulty in handling studies, assignments, and exams.",
                "Academic pressure"));

        hardQuestions.add(new QuestionHard(
                "Psychological struggles that affect learning.",
                "Mental health issues"));

        hardQuestions.add(new QuestionHard(
                "Struggle due to insufficient money for education.",
                "Financial constraints"));

        hardQuestions.add(new QuestionHard(
                "Lack of drive to continue studying.",
                "Lack of motivation"));

        hardQuestions.add(new QuestionHard(
                "Problems with friends, peers, or the community.",
                "Social issues"));

        hardQuestions.add(new QuestionHard(
                "The skill of finishing more work in less time.",
                "Time management"));

        hardQuestions.add(new QuestionHard(
                "Challenge related to lack of gadgets or internet access.",
                "Technological barriers"));

        hardQuestions.add(new QuestionHard(
                "Problems caused by arguments or responsibilities at home.",
                "Family problems"));

        hardQuestions.add(new QuestionHard(
                "A strategy where students set specific, measurable, achievable, relevant, and time-bound goals.",
                "SMART goals"));

        hardQuestions.add(new QuestionHard(
                "Participating in school and classroom activities makes a student an _____.",
                "Active member"));
    }

    private void EducationandHard() {
        hardQuestions = new ArrayList < > ();

        hardQuestions.add(new QuestionHard("The process of acquiring or modifying knowledge, skills, values, and behaviors.", "Learning"));
        hardQuestions.add(new QuestionHard("The process of imparting knowledge, skills, and values through teaching.", "Education"));
        hardQuestions.add(new QuestionHard("The type of education that happens in schools with a structured curriculum.", "Formal Education"));
        hardQuestions.add(new QuestionHard("Education that occurs naturally in daily life experiences.", "Informal Education"));
        hardQuestions.add(new QuestionHard("Education that includes workshops, seminars, and community-based programs.", "Non-formal Education"));
        hardQuestions.add(new QuestionHard("The intelligence that allows a person to think in rhythms, patterns, and sounds.", "Musical "));
        hardQuestions.add(new QuestionHard("The intelligence that helps a person understand their own feelings and motivations.", "Intrapersonal "));
        hardQuestions.add(new QuestionHard("The intelligence used when solving math problems and reasoning logically.", "Logical-Mathematical "));
        hardQuestions.add(new QuestionHard("The intelligence of people who are skilled at using language effectively in speaking and writing.", "Verbal-Linguistic "));
        hardQuestions.add(new QuestionHard("The intelligence of people who have strong awareness and connection with nature.", "Naturalist "));
        hardQuestions.add(new QuestionHard("The intelligence of people who can visualize and imagine objects, maps, or spatial designs.", "Visual-Spatial "));
        hardQuestions.add(new QuestionHard("The intelligence of people who can socialize, work well, and understand others’ feelings.", "Interpersonal "));
        hardQuestions.add(new QuestionHard("The intelligence that focuses on movement, body control, and physical activity.", "Bodily-Kinesthetic "));
        hardQuestions.add(new QuestionHard("The intelligence related to asking deep questions about human life and existence.", "Existential "));
        hardQuestions.add(new QuestionHard("The most powerful tool that improves lives, provides opportunities, and gives meaning to the world.", "Education"));
    }

    private void impEducationandHard() {
        hardQuestions = new ArrayList < > ();

        hardQuestions.add(new QuestionHard("The process of facilitating learning and acquiring knowledge, skills, values, beliefs, and habits.", "Education"));
        hardQuestions.add(new QuestionHard("The process of acquiring new or modifying existing knowledge, behaviors, skills, values, or preferences.", "Learning"));
        hardQuestions.add(new QuestionHard("The primary goal of education that transmits understanding and information about the world.", "Impart Knowledge"));
        hardQuestions.add(new QuestionHard("The primary goal of education that provides practical abilities for tasks and problem-solving.", "Impart Skills"));
        hardQuestions.add(new QuestionHard("The primary goal of education that helps shape character and guide decisions through ethical foundations.", "Impart Values and Beliefs"));
        hardQuestions.add(new QuestionHard("The primary goal of education that instills disciplined routines for success.", "Impart Beneficial Habits"));
        hardQuestions.add(new QuestionHard("The goal of learning that involves continuously gaining new knowledge and perspectives.", "Acquire New Understanding"));
        hardQuestions.add(new QuestionHard("The goal of learning that means changing existing actions and abilities to improve effectiveness.", "Modify Behaviors and Skills"));
        hardQuestions.add(new QuestionHard("The goal of learning that involves reflecting on and adjusting core values, likes, and dislikes.", "Reevaluate Values and Preferences"));
        hardQuestions.add(new QuestionHard("The vision describes building a ________ educational community of servant leaders with truth and compassion.", "God-loving"));

        hardQuestions.add(new QuestionHard("Anything that causes stress, whether physical, emotional, or psychological.", "Stressor"));
        hardQuestions.add(new QuestionHard("The type of stress that motivates people and leads to growth and achievement.", "Eustress"));
        hardQuestions.add(new QuestionHard("The type of stress that harms performance and health.", "Distress"));
        hardQuestions.add(new QuestionHard("The body’s natural reaction to stress that prepares a person to face danger or escape.", "Fight-or-Flight Response"));
        hardQuestions.add(new QuestionHard("Stress that comes from exams, deadlines, and workload in school or university.", "Academic Stress"));
    }

    // Question display methods
    // Add this helper method to check question length
    private boolean isLongQuestion(String questionText) {
        return questionText.length() > 70;
    }

    // Update the startTimer methods to use dynamic timing
    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        Question current = questions.get(currentIndex);
        long timePerQuestion = isLongQuestion(current.getText()) ? 40000 : 30000;

        countDownTimer = new CountDownTimer(timePerQuestion, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText((millisUntilFinished / 1000) + "s");
            }

            public void onFinish() {
                Toast.makeText(quizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                goToNextQuestion();
            }
        };
        countDownTimer.start();
    }

    private void startMediumTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        QuestionMed current = mediumquestions.get(currentIndex);
        long timePerQuestion = isLongQuestion(current.getText()) ? 40000 : 30000;

        countDownTimer = new CountDownTimer(timePerQuestion, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText((millisUntilFinished / 1000) + "s");
            }

            public void onFinish() {
                Toast.makeText(quizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                goToNextMediumQuestion();
            }
        };
        countDownTimer.start();
    }

    private void startHardTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        QuestionHard current = hardQuestions.get(currentIndex);
        long timePerQuestion = isLongQuestion(current.getText()) ? 40000 : 30000;

        countDownTimer = new CountDownTimer(timePerQuestion, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText((millisUntilFinished / 1000) + "s");
            }

            public void onFinish() {
                Toast.makeText(quizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                goToNextHardQuestion();
            }
        };
        countDownTimer.start();
    }

    // Update the showQuestion methods to use the correct timer method
    private void showQuestion() {
        if (currentIndex >= questions.size()) {
            if (!isHardQuizFinished) {
                isHardQuizFinished = true;
                finishQuiz();
            }
            return;
        }

        optionsGroup.clearCheck();
        Question current = questions.get(currentIndex);
        questionText.setText((currentIndex + 1) + ". " + current.getText());
        startTimer(); // This will now use the dynamic timing
    }

    private void showMediumQuestion() {
        if (currentIndex >= mediumquestions.size()) {
            if (!isHardQuizFinished) {
                isHardQuizFinished = true;
                finishMediumQuiz();
            }
            return;
        }

        optionsGroup.clearCheck();
        QuestionMed current = mediumquestions.get(currentIndex);
        questionText.setText((currentIndex + 1) + ". " + current.getText());
        optionA.setText(current.getOptions()[0]);
        optionB.setText(current.getOptions()[1]);
        optionC.setText(current.getOptions()[2]);
        optionD.setText(current.getOptions()[3]);
        startMediumTimer(); // This will now use the dynamic timing
    }

    private void showHardQuestion() {
        if (currentIndex >= hardQuestions.size()) {
            if (!isHardQuizFinished) {
                isHardQuizFinished = true;
                finishHardQuiz();
            }
            return;
        }

        answerInput.setText("");
        QuestionHard current = hardQuestions.get(currentIndex);
        questionText.setText((currentIndex + 1) + ". " + current.getText());
        startHardTimer(); // This will now use the dynamic timing
    }

    // Evaluation methods
    private void evaluateAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            userEasyAnswers.add(null); // Store null if no answer
            return;
        }

        boolean selectedAnswer = (selectedId == R.id.optionTrue);
        userEasyAnswers.add(selectedAnswer);

        if (selectedAnswer == questions.get(currentIndex).isAnswerTrue()) {
            score++;
        }
    }

    private void evaluateMediumAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        int selectedIndex = -1;

        if (selectedId == optionA.getId()) selectedIndex = 0;
        else if (selectedId == optionB.getId()) selectedIndex = 1;
        else if (selectedId == optionC.getId()) selectedIndex = 2;
        else if (selectedId == optionD.getId()) selectedIndex = 3;

        userMediumAnswers.add(selectedIndex);

        if (selectedIndex == mediumquestions.get(currentIndex).getCorrectAnswerIndex()) {
            score++;
        }
    }

    private void evaluateHardAnswer() {
        String userAnswer = answerInput.getText().toString().trim();
        String correctAnswer = hardQuestions.get(currentIndex).getAnswer().trim();

        userHardAnswers.add(userAnswer);

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            score++;
        }
    }

    // Navigation methods
    private void goToNextQuestion() {
        if (countDownTimer != null) countDownTimer.cancel();
        currentIndex++;
        showQuestion();
    }

    private void goToNextMediumQuestion() {
        if (countDownTimer != null) countDownTimer.cancel();
        currentIndex++;
        showMediumQuestion();
    }

    private void goToNextHardQuestion() {
        if (countDownTimer != null) countDownTimer.cancel();
        currentIndex++;
        showHardQuestion();
    }

    // Finish methods
    private void finishQuiz() {
        showResultDialog(questions.size());
    }

    private void finishMediumQuiz() {
        showResultDialog(mediumquestions.size());
    }

    private void finishHardQuiz() {
        showResultDialog(hardQuestions.size());
    }

    public void showResultDialog(int totalQuestions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_quiz_result, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
        dialogMessage.setText("Your score: " + score + "/" + totalQuestions);

        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        Button buttonReview = dialogView.findViewById(R.id.buttonReview);
        buttonReview.setOnClickListener(v -> {
            dialog.dismiss();
            showReviewScreen();
        });

        dialog.show();
    }

    private void showReviewScreen() {
        isReviewMode = true;
        setContentView(R.layout.activity_review_answers);

        Button backButton = findViewById(R.id.backButton);
        TextView reviewText = findViewById(R.id.reviewText);
        TextView headertext = findViewById(R.id.headerText);
        TextView scoretext = findViewById(R.id.scoretext);

        StringBuilder reviewContent = new StringBuilder();
        String difficulty = getIntent().getStringExtra("difficulty");

        // Set common header
        headertext.setText("REVIEW ANSWERS");
        scoretext.setText(String.format("Score: %d/%d", score, 15));

        switch (difficulty) {
            case "Easy":
                for (int i = 0; i < questions.size(); i++) {
                    Question q = questions.get(i);
                    Boolean userAnswer = i < userEasyAnswers.size() ? userEasyAnswers.get(i) : null;
                    boolean correctAnswer = q.isAnswerTrue();

                    reviewContent.append(String.format("<b>%d</b>: %s<br>", i + 1, q.getText()));

                    if (userAnswer == null) {
                        reviewContent.append("<b>Your Answer: <font color=\"#FF8C42\">⚠️ No answer</font></b><br>");
                    } else if (userAnswer == correctAnswer) {
                        reviewContent.append("<b><font color=\"#00BA00\">✅ 1 Point</font></b><br>");
                    } else {
                        reviewContent.append(String.format(
                                "<b>Your Answer: <font color=\"#EE685C\">%s ❌</font></b><br>",
                                userAnswer ? "True" : "False"
                        ));
                    }

                    reviewContent.append(String.format(
                            "<b>Correct Answer: <font color=\"#00BA00\">%s</font></b><br><br>",
                            correctAnswer ? "True" : "False"
                    ));
                }
                break;

            case "Medium":
                for (int i = 0; i < mediumquestions.size(); i++) {
                    QuestionMed q = mediumquestions.get(i);
                    int correctIndex = q.getCorrectAnswerIndex();
                    Integer userAnswerIndex = i < userMediumAnswers.size() ? userMediumAnswers.get(i) : -1;

                    String correctAnswer = q.getOptions()[correctIndex];
                    String userAnswer = userAnswerIndex != -1 ? q.getOptions()[userAnswerIndex] : null;

                    reviewContent.append(String.format("<b>%d</b>: %s<br>", i + 1, q.getText()));

                    if (userAnswer == null) {
                        reviewContent.append("<b>Your Answer: <font color=\"#FF8C42\">⚠️ No answer</font></b><br>");
                    } else if (userAnswer.equals(correctAnswer)) {
                        reviewContent.append("<b><font color=\"#00BA00\">✅ 1 Point</font></b><br>");
                    } else {
                        reviewContent.append(String.format(
                                "<b>Your Answer: <font color=\"#EE685C\">%s ❌</font></b><br>",
                                userAnswer
                        ));
                    }

                    reviewContent.append(String.format(
                            "<b>Correct Answer: <font color=\"#00BA00\">%s</font></b><br><br>",
                            correctAnswer
                    ));
                }
                break;

            case "Hard":
                for (int i = 0; i < hardQuestions.size(); i++) {
                    QuestionHard q = hardQuestions.get(i);
                    String correctAnswer = q.getAnswer();
                    String userAnswer = i < userHardAnswers.size() ? userHardAnswers.get(i) : null;

                    reviewContent.append(String.format("<b>%d</b>: %s<br>", i + 1, q.getText()));

                    if (TextUtils.isEmpty(userAnswer)) {
                        reviewContent.append("<b>Your Answer: <font color=\"#FF8C42\">⚠️ No answer</font></b><br>");
                    } else if (userAnswer.equals(correctAnswer)) {
                        reviewContent.append("<b><font color=\"#00BA00\">✅ 1 Point</font></b><br>");
                    } else {
                        reviewContent.append(String.format(
                                "<b>Your Answer: <font color=\"#EE685C\">%s ❌</font></b><br>",
                                userAnswer
                        ));
                    }

                    reviewContent.append(String.format(
                            "<b>Correct Answer: <font color=\"#00BA00\">%s</font></b><br><br>",
                            correctAnswer
                    ));
                }
                break;
        }

        // Format and set styled text
        Spanned formattedText;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            formattedText = Html.fromHtml(reviewContent.toString(), Html.FROM_HTML_MODE_LEGACY);
        } else {
            formattedText = Html.fromHtml(reviewContent.toString());
        }

        reviewText.setText(formattedText);

        backButton.setOnClickListener(v -> finish());
    }

    // Question classes
    private static class Question {
        private final String text;
        private final boolean answerTrue;

        public Question(String text, boolean answerTrue) {
            this.text = text;
            this.answerTrue = answerTrue;
        }

        public String getText() {
            return text;
        }

        public boolean isAnswerTrue() {
            return answerTrue;
        }
    }

    private static class QuestionMed {
        private final String text;
        private final String[] options;
        private final int correctAnswerIndex;

        public QuestionMed(String text, String[] options, int correctAnswerIndex) {
            this.text = text;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        public String getText() {
            return text;
        }

        public String[] getOptions() {
            return options;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }
    }

    private static class QuestionHard {
        private final String text;
        private final String answer;

        public QuestionHard(String text, String answer) {
            this.text = text;
            this.answer = answer;
        }

        public String getText() {
            return text;
        }
        public String getAnswer() {
            return answer;
        }
    }
}