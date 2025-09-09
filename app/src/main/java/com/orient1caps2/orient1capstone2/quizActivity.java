package com.orient1caps2.orient1capstone2;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.Spanned;
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
    private List<Question> questions;
    private List<QuestionMed> mediumquestions;
    private List<QuestionHard> hardQuestions;

    // Game State
    private int currentIndex = 0;
    private int score = 0;
    private CountDownTimer countDownTimer;
    // Add these with other class variables
    private final List<Boolean> userEasyAnswers = new ArrayList<>();
    private final List<Integer> userMediumAnswers = new ArrayList<>();
    private final List<String> userHardAnswers = new ArrayList<>();

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

        });

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
        questions = new ArrayList<>();
        questions.add(new Question("Fr.Mariano M Sablay is the Parish Priest of San Nicolas de Tolentino of Capas Tarlac.", true));
        questions.add(new Question("San Nicolas was founded the year 1946.", false));
        questions.add(new Question("San Nicolas Academy had 37 Students.", false));
        questions.add(new Question("The First Commencement exercises is March 1950.", true));
        questions.add(new Question("St. Dominic De Guzman was the Patron Saint of DCT.", false));
        questions.add(new Question("Last commencement exercises is April 1973.", true));
        questions.add(new Question("The Tertiary Education was open in 1980.", false));
        questions.add(new Question("Sr. Caridad Bayani was the first Principalof San Nicolas Academy.", true));
        questions.add(new Question("In April 20 1999, The School was officially renamed Dominican College of Tarlac.", false));
        questions.add(new Question("Computer Secretarial was a 4 year course.", true));
        questions.add(new Question("In 2005-2006, Dominica College of Tarlac has been given accreditation by TESDA.", false));
        questions.add(new Question("In 2005-2006, Dominica College of Tarlac has been given accreditation by TESDA.", false));
        questions.add(new Question("FIDES means “Faith in God.", false));
        questions.add(new Question("PATRIA means “Love for country and fellowmen”.", true));
        questions.add(new Question("SAPIENTIA means “Wisdom”", true));
    }

    public void loadQuestionStudyandPrayer() {
        questions = new ArrayList<>();
        questions.add(new Question("St. Dominic De Guzman was the founder of the Order of Preachers.", true));
        questions.add(new Question("The Feast day of St. Dominic is August 6.", false));
        questions.add(new Question(" Dominic attend the University of Palencia at age 14.", true));
        questions.add(new Question(" A cross is what Dominic's godmother perceive on his forehead at baptism.", true));
        questions.add(new Question("St. Dominic de Guzman was born in Caleruga, Spain.", true));
        questions.add(new Question("St Dominic De Guzman was born in the year 1221.", false));
        questions.add(new Question("St. Dominic De Guzman has 4 siblings.", false));
        questions.add(new Question("St. Dominic de Guzman's parents were Flix de Guzman and Juana de Aza.", true));
        questions.add(new Question("  He was a fisherman that gives alms to the poor.", false));
        questions.add(new Question(" There are four subjects included in the Quadrivium that Dominic studied.", true));
        questions.add(new Question(" Albigensianism belief considered all material things are good.", false));
        questions.add(new Question("St. Dominic died on August 8, 1170.", false));
        questions.add(new Question(" St Dominic gathered 9 women in Prouille and formed them as nuns.", true));
        questions.add(new Question(" Dominic spoke to Pope Innocent III about starting a new group focused on preaching.", true));
        questions.add(new Question("As a child, Dominic avoided games and slept on the floor instead of a bed.", true));
    }

    public void loadQuestionIntroductionOnStudentLife() {
        questions = new ArrayList<>();
        questions.add(new Question("Student life is considered the most important and golden period of development.", true));
        questions.add(new Question("Doing homework and attending classes are main academic tasks of students.", true));
        questions.add(new Question("Balancing schoolwork with hobbies and social life is part of time management.", true));
        questions.add(new Question("Studying in a group helps students learn more effectively.", true));
        questions.add(new Question("Students should compare themselves to others to improve faster.", false));
        questions.add(new Question("Mental health issues are a common problem faced by students.", true));
        questions.add(new Question("Students usually have no problems when it comes to finances.", false));
        questions.add(new Question("Social issues can be a significant challenge in student life.", true));
        questions.add(new Question("Technological barriers include lack of access to devices or internet..", true));
        questions.add(new Question("Making a timeline of one’s educational journey is a good way to reflect.", true));
        questions.add(new Question("Time management helps students do more and better work in less time.", true));
        questions.add(new Question("Being rejected and forming friendships can be unforgettable student experiences.", true));
        questions.add(new Question("A favorite teacher can have a significant impact on a student’s life.", true));
        questions.add(new Question("Academic knowledge helps students learn discipline and good behavior.", true));
        questions.add(new Question("Paying attention in class is one way to increase engagement.", true));
    }

    public void loadQuestionEducationAndLearning() {
        questions = new ArrayList<>();
        questions.add(new Question("Education is the process of imparting knowledge, skills, values, beliefs, and habits.", true));
        questions.add(new Question("Learning is the process of forgetting unnecessary information.", false));
        questions.add(new Question("Formal education happens naturally through life experiences and self-directed learning.", false));
        questions.add(new Question("Informal education occurs outside the classroom and is often unstructured.", true));
        questions.add(new Question(" Non-formal education is highly structured with specific timeframes and learning goals.", false));
        questions.add(new Question("Education helps individuals find meaning and can greatly improve lives.", true));
        questions.add(new Question("Musical intelligence refers to a strong ability to recognize rhythms, sounds, and patterns.", true));
        questions.add(new Question("Intrapersonal intelligence refers to the ability to interact well with other people.", false));
        questions.add(new Question("Naturalistic intelligence involves being in tune with nature and the environment.", true));
        questions.add(new Question("Verbal/Linguistic intelligence includes skills in writing and speaking.", true));
        questions.add(new Question("Interpersonal intelligence is about understanding and interacting with others effectively.", true));
        questions.add(new Question("Spatial intelligence is the ability to solve mathematical problems mentally.", false));
        questions.add(new Question("Bodily-kinesthetic intelligence relates to control of body movements and physical actions.", true));
        questions.add(new Question("Logical-mathematical intelligence involves reasoning and analyzing patterns.", true));
        questions.add(new Question("Existential intelligence is the ability to explore deep questions about life and existence.", true));
    }

    public void loadQuestionImportanceEducationAndLearning() {
        questions = new ArrayList<>();
        questions.add(new Question("A stressor is something that reduces stress and promotes relaxation.", false));
        questions.add(new Question("Stress is your body and mind's response to overwhelming, pressured, or worrying situations.", true));
        questions.add(new Question("Eustress is a harmful type of stress that leads to health issues.", false));
        questions.add(new Question("Distress refers to positive stress that helps you grow and perform better.", false));
        questions.add(new Question("Personal stressors often arise from one's daily life and emotional well-being.", true));
        questions.add(new Question("Academic stressors are mainly related to pressures at home, like chores and family conflict.", false));
        questions.add(new Question("Professional stress can result from lack of job security or opportunities for career growth.", true));
        questions.add(new Question("Financial pressure, such as budgeting problems, is an example of an environmental stressor.", false));
        questions.add(new Question("Noise pollution in urban areas can contribute to environmental stress.", true));
        questions.add(new Question("Muscle relaxation is a physical symptom of stress.", false));
        questions.add(new Question("Job insecurity is a factor that can cause professional stress.", true));
        questions.add(new Question("Indecisiveness is a behavioral symptom caused by physical exhaustion.", false));
        questions.add(new Question("Maintaining a healthy lifestyle can significantly reduce stress levels.", true));
        questions.add(new Question("Isolating yourself from friends and family is a healthy way to manage stress.", false));
        questions.add(new Question("A poor work-life balance can lead to increased levels of stress.", true));
    }

    // Medium question loading methods
    private void dctcultmedium() {
        mediumquestions = new ArrayList<>();
        mediumquestions.add(new QuestionMed("In the first commencement exercise in 1950, how many students graduated?",
                new String[]{"11", "12", "13", "14"}, 3));
        mediumquestions.add(new QuestionMed("San Nicolas Academy was established on February 14, 1947 founded by?",
                new String[]{"Sr. Marisor Fabros", "Fr. Benny Lapitan", "Fr. Mariano M. Sablay", "Sr. Caridad Bayani"}, 2));
        mediumquestions.add(new QuestionMed("When was the last commencement exercises?",
                new String[]{"March 1971", "March 1973", "March 1961", "March 1980"}, 1));
        mediumquestions.add(new QuestionMed("What course was recognized by the Government in 1997?",
                new String[]{"Bachelor of Business Administration", "Bachelor of Hospitality Management", "Bachelor of Secondary Education", "Bachelor of Elementary Education"}, 3));
        mediumquestions.add(new QuestionMed("When is the feast day of St. Dominic De Guzman?",
                new String[]{"August 3", "August 7", "August 8", "August 4"}, 2));
        mediumquestions.add(new QuestionMed("What does the STEM strand prepare students for?",
                new String[]{"Careers in hospitality", "Careers in business", "Careers in STEM", "Undecided"}, 2));
        mediumquestions.add(new QuestionMed("What industry is the F & B strand tailored for?",
                new String[]{"Technology", "Education", "Hospitality and Culinary Industries", "Criminal Justice"}, 2));
        mediumquestions.add(new QuestionMed("What is the focus of the BSIT program?",
                new String[]{"Software Development, Cybersecurity, and Network Administration", "Marketing Management", "Forensic Science", "Event Planning"}, 0));
        mediumquestions.add(new QuestionMed("San Nicolas Academy had _____ students.",
                new String[]{"36", "35", "28", "30"}, 1));
        mediumquestions.add(new QuestionMed("The First Commencement exercises is?",
                new String[]{"March 1950", "April 1960", "March 1947", "April 1950"}, 0));
        mediumquestions.add(new QuestionMed("Who is the first principal in San Nicolas Academy?",
                new String[]{"Sr. Ma. Rosalina Mirabueno, O.P", "Sr. Caridad Bayani, O.P", "Sr. Ma. Magdalena Olfato, O.P", "Sr. Ma. Magdalena Olfato, O.P"}, 2));
        mediumquestions.add(new QuestionMed("San Nicolas was founded the year _____ .",
                new String[]{"1950", "1970", "1947", "1960"}, 2));
        mediumquestions.add(new QuestionMed("The Tertiary Education was open in ______ .",
                new String[]{"1950", "1960", "1947", "1980"}, 3));
        mediumquestions.add(new QuestionMed("When was DCT given accreditation by TESDA?",
                new String[]{"2005-2007", "2005-2006", "2004-2005", "2006-2007"}, 1));
        mediumquestions.add(new QuestionMed("FIDES means?",
                new String[]{"Faith in God", "Love for country and fellowmen", "Wisdom", "None of the above"}, 0));
    }

    //Study and Prayer Life
    private void studandpraymedium() {
        mediumquestions = new ArrayList<>();
        mediumquestions.add(new QuestionMed(" Who founded the Order of Preachers?",
                new String[]{"St. Thomas Aquinas", "St. Francis of Assisi", "St. Dominic de Guzman", "St. Augustine"}, 2));
        mediumquestions.add(new QuestionMed("Where was St. Dominic de Guzman born?",
                new String[]{"Madrid, Spain", "Valencia, Spain", "Caleruga, Spain", "Barcelona, Spain"}, 2));
        mediumquestions.add(new QuestionMed("In what year was St. Dominic de Guzman born?",
                new String[]{"1203", "1170", "1185", "1221"}, 1));
        mediumquestions.add(new QuestionMed("Who were the parents of St. Dominic de Guzman?",
                new String[]{"Juan de Aza and Maria Guzman", "Felix de Guzman and Juana de Aza", "Peter Guzman and Juana de la Cruz", "Antonio de Guzman and Maria de Aza"}, 1));
        mediumquestions.add(new QuestionMed("Which of the following were siblings of St. Dominic?",
                new String[]{"Paul and Matthew", "Anthony and Mannes", "James and Philip", "Peter and Simon"}, 1));
        mediumquestions.add(new QuestionMed("What did Dominic’s godmother see on his forehead during baptism?",
                new String[]{"A flame", "A dove", "A star", "A cross"}, 2));
        mediumquestions.add(new QuestionMed("At what age did Dominic attend the University of Palencia?",
                new String[]{"12", "14", "16", "18"}, 0));
        mediumquestions.add(new QuestionMed("What did Dominic sell to help the poor?",
                new String[]{"His precious books", "His clothes", "His jewelry", "His home"}, 0));
        mediumquestions.add(new QuestionMed("Which of the following is part of the Quadrivium studied by Dominic?",
                new String[]{"Music", "Literature", "Rhetoric", "Philosophy"}, 0));
        mediumquestions.add(new QuestionMed("What heresy did Dominic encounter in 1203?",
                new String[]{"Gnosticism", "Arianism", "Albigensianism", "Pelagianism"}, 2));
        mediumquestions.add(new QuestionMed("What did the Albigensians believe about material things?",
                new String[]{"They are holy", "They are temporary blessings", "They are necessary evils", "They are evil"}, 3));
        mediumquestions.add(new QuestionMed("What was the name of the religious order approved in 1216 by Pope Honorius III?",
                new String[]{"Society of Jesus", "Order of Preachers", "Order of St. Benedict", "Franciscan Order"}, 1));
        mediumquestions.add(new QuestionMed("Who approved St. Dominic's Order?.",
                new String[]{"A king", "A bishop", "Pope Honorius III", "Pope Innocent III"}, 2));
        mediumquestions.add(new QuestionMed("What was St. Dominic's main profession?",
                new String[]{"Farmer", "Teacher", "Preacher", "King"}, 2));
        mediumquestions.add(new QuestionMed("Besides studying the Trivium, what other set of subjects did St. Dominic study? ",
                new String[]{"The Classics", "The Quadrivium", "Canon Law", "Astrology"}, 1));
    }

    private void introductiononStudentLifeMed() {
        mediumquestions = new ArrayList<>();
        mediumquestions.add(new QuestionMed("What is considered the most important and golden period of development?",
                new String[]{"Childhood", "Student Life", "Career stage", "Retirement"}, 1));
        mediumquestions.add(new QuestionMed("Which of the following is an academic task students typically do?",
                new String[]{"Going to classes", "Attending parties", "Traveling", "Watching TV"}, 0));
        mediumquestions.add(new QuestionMed("What is one key aspect of managing time effectively as a student?",
                new String[]{"Balancing schoolwork with hobbies", "Ignoring social life", "Focusing only on studies", "Avoiding physical activity"}, 0));
        mediumquestions.add(new QuestionMed("What is one benefit of studying in a group?",
                new String[]{"Learn more effectively", "Spend less time learning", "Avoid doing homework", "Skip class together"}, 0));
        mediumquestions.add(new QuestionMed("Why should students avoid comparing themselves to others?",
                new String[]{"It makes them more competitive", "It helps them be like everyone else", "Everyone has their own abilities and capabilities", "It improves their popularity"}, 2));
        mediumquestions.add(new QuestionMed("What is a common mental health issue that students face?",
                new String[]{"Anxiety and stress", "Laziness", "Sleepiness", "Disinterest in food"}, 0));
        mediumquestions.add(new QuestionMed("What is a common financial challenge for students?",
                new String[]{"Overspending on gadgets", "Financial constraints", "Owning too many things", "Budgeting excess money"}, 1));
        mediumquestions.add(new QuestionMed("What is a significant social challenge a student might encounter?",
                new String[]{"Managing group projects", "Learning a new skill", "Social issues", "Attending online classes"}, 2));
        mediumquestions.add(new QuestionMed("What is a common technological barrier for students?",
                new String[]{"Too many apps", "Noisy environment", "Lack of access to devices or internet", "Disliking social media"}, 2));
        mediumquestions.add(new QuestionMed("What is one activity students can do to reflect on their educational journey?",
                new String[]{"Write a poem", "Interview their friends", "Make a timeline of their education history", "Create a school vlog"}, 2));
        mediumquestions.add(new QuestionMed("What is an unforgettable experience students might have?",
                new String[]{"Memorizing a textbook", "Being rejected and forming friendships", "Missing a class", "Doing group work"}, 1));
        mediumquestions.add(new QuestionMed("What impact can a favorite teacher have on a student?",
                new String[]{"Provide snacks after class", "Significantly influence their life", "Allow more free time", "Skip lessons"}, 1));
        mediumquestions.add(new QuestionMed("What is one importance of academic learning in student life?",
                new String[]{"Winning competitions", "Learning manners and discipline", "Earning early income", "Traveling the world"}, 1));
        mediumquestions.add(new QuestionMed("What is one way to enhance classroom engagement?",
                new String[]{"Pay attention to what teachers teach", "Use your phone silently", "Talk to classmates", "Take random notes"}, 0));
    }

    private void educationAndLearningmed() {
        mediumquestions = new ArrayList<>();
        mediumquestions.add(new QuestionMed("What is the process of imparting knowledge, skills, values, beliefs, and habits to individuals?",
                new String[]{"Education", "Learning", "Training", "Discipline"}, 0));
        mediumquestions.add(new QuestionMed("What refers to acquiring or modifying knowledge, behaviors, skills, or preferences?",
                new String[]{"Teaching", "Learning", "Memorizing", "Instructing"}, 1));
        mediumquestions.add(new QuestionMed("What kind of education is structured and usually takes place in schools with a set curriculum?",
                new String[]{"Formal Education", "Informal Education", "Non-formal Education", "Self-Education"}, 0));
        mediumquestions.add(new QuestionMed("What type of education occurs outside classrooms and often happens through life experiences?",
                new String[]{"Structured Education", "Informal Education", "Formal Education", "Academic Education"}, 1));
        mediumquestions.add(new QuestionMed("What kind of learning results from daily activities and is not organized or structured?",
                new String[]{"Informal Education", "Non-formal Education", "Formal Education", "Advanced Learning"}, 1));
        mediumquestions.add(new QuestionMed("Why is education considered powerful?",
                new String[]{"It provides money", "It helps find meaning and improves lives", "It teaches physical strength", "It helps avoid responsibilities"}, 1));
        mediumquestions.add(new QuestionMed("What intelligence involves thinking in patterns, rhythms, and sounds?",
                new String[]{"Musical Intelligence", "Spatial Intelligence", "Logical-Mathematical Intelligence", "Interpersonal Intelligence"}, 0));
        mediumquestions.add(new QuestionMed("Which intelligence is associated with self-awareness and understanding one’s emotions?",
                new String[]{"Interpersonal Intelligence", "Intrapersonal Intelligence", "Existential Intelligence", "Naturalistic Intelligence"}, 1));
        mediumquestions.add(new QuestionMed("What intelligence is shown by those in tune with nature and the environment?",
                new String[]{"Intrapersonal Intelligence", "Spatial Intelligence", "Naturalistic Intelligence", "Logical-Mathematical Intelligence"}, 2));
        mediumquestions.add(new QuestionMed("Which intelligence is demonstrated by individuals good at speaking and writing?",
                new String[]{"Intrapersonal Intelligence", "Verbal/Linguistic Intelligence", "Musical Intelligence", "Naturalistic Intelligence"}, 1));
        mediumquestions.add(new QuestionMed("People who are skilled at understanding and working with others possess what intelligence?",
                new String[]{"Intrapersonal Intelligence", "Bodily-Kinesthetic Intelligence", "Interpersonal Intelligence", "Spatial Intelligence"}, 2));
        mediumquestions.add(new QuestionMed("What intelligence is associated with the ability to visualize and mentally manipulate objects?",
                new String[]{"Logical-Mathematical Intelligence", "Spatial Intelligence", "Musical Intelligence", "Verbal Intelligence"}, 1));
        mediumquestions.add(new QuestionMed("Individuals who excel in physical movement and coordination have what intelligence?",
                new String[]{"Spatial Intelligence", "Bodily-Kinesthetic Intelligence", "Intrapersonal Intelligence", "Naturalistic Intelligence"}, 1));
        mediumquestions.add(new QuestionMed("What intelligence involves reasoning, pattern recognition, and problem-solving?",
                new String[]{"Verbal/Linguistic Intelligence", "Spatial Intelligence", "Logical-Mathematical Intelligence", "Interpersonal Intelligence"}, 2));
        mediumquestions.add(new QuestionMed("What intelligence refers to the ability to explore deeper life questions and existence?",
                new String[]{"Naturalistic Intelligence", "Existential Intelligence", "Intrapersonal Intelligence", "Musical Intelligence"}, 1));
    }

    private void impeducationAndLearningmed() {
        mediumquestions = new ArrayList<>();
        mediumquestions.add(new QuestionMed("What is anything that causes stress or triggers a mental, emotional, or physical reaction?",
                new String[]{"Emotion", "Stress", "Anxiety", "Stressor"}, 3));
        mediumquestions.add(new QuestionMed("What is the body and mind’s reaction to overwhelming, pressured, or worrying situations?",
                new String[]{"Calmness", "Stress", "Relief", "Peace"}, 1));
        mediumquestions.add(new QuestionMed("Which type of stress is beneficial and helps motivate individuals?",
                new String[]{"Anxiety", "Depression", "Eustress", "Distress"}, 2));
        mediumquestions.add(new QuestionMed("What type of stress is harmful and leads to anxiety or health issues?",
                new String[]{"Distress", "Eustress", "Motivation", "Calmness"}, 0));
        mediumquestions.add(new QuestionMed("Which stressor arises from emotional well-being and daily life?",
                new String[]{"Academic Stressor", "Environmental Stressor", "Personal Stressor", "Professional Stressor"}, 2));
        mediumquestions.add(new QuestionMed("Which stressor is commonly experienced by students due to school pressure?",
                new String[]{"Personal Stressor", "Academic Stressor", "Professional Stressor", "Social Stressor"}, 1));
        mediumquestions.add(new QuestionMed("What stressor comes from work responsibilities and job-related pressures?",
                new String[]{"Environmental Stressor", "Professional Stressor", "Personal Stressor", "Relationship Stressor"}, 1));
        mediumquestions.add(new QuestionMed("Which of the following is an example of a personal stressor?",
                new String[]{"Natural disasters", "Financial pressure", "Heavy traffic", "Group projects"}, 1));
        mediumquestions.add(new QuestionMed("Which of the following contributes to environmental stress?",
                new String[]{"Academic exams", "Job interviews", "Noise pollution", "Romantic breakups"}, 2));
        mediumquestions.add(new QuestionMed("What physical symptom of stress results in soreness in the neck, shoulders, or back?",
                new String[]{"Fatigue", "Muscle tension or pain", "Headache", "Skin irritation"}, 1));
        mediumquestions.add(new QuestionMed("What workplace-related issue contributes to professional stress?",
                new String[]{"Lack of internet", "Job insecurity", "Sleep schedule", "Weight loss"}, 1));
        mediumquestions.add(new QuestionMed("What mental symptom involves difficulty choosing between options due to stress?",
                new String[]{"Overconfidence", "Indecisiveness", "Alertness", "Motivation"}, 1));
        mediumquestions.add(new QuestionMed("Which of the following helps in reducing stress through balanced nutrition and rest?",
                new String[]{"Time management", "Breathing exercises", "Healthy lifestyle choices", "Isolation"}, 2));
        mediumquestions.add(new QuestionMed("What strategy involves seeking emotional help from friends or family?",
                new String[]{"Self-isolation", "Journaling", "Social support", "Denial"}, 2));
        mediumquestions.add(new QuestionMed("Which situation describes a cause of stress due to imbalance between job and personal time?",
                new String[]{"Noise pollution", "Poor work-life balance", "Academic pressure", "Mental illness"}, 1));
    }

    // Hard question loading methods
    private void dctcultHard() {
        hardQuestions = new ArrayList<>();
        hardQuestions.add(new QuestionHard("Who founded San Nicolas Academy?", "Fr. Mariano M. Sablay"));
        hardQuestions.add(new QuestionHard("What year was San Nicolas Academy founded?", "1947"));
        hardQuestions.add(new QuestionHard("How many students graduated in the first graduation in 1950?", "14"));
        hardQuestions.add(new QuestionHard("What is the exact date of the feast day of St. Dominic de Guzman?", "August 8"));
        hardQuestions.add(new QuestionHard("What course was recognized by the government in 1997?", "Bachelor of Elementary Education"));
        hardQuestions.add(new QuestionHard("What religious group started managing the school in the 1960s?", "Dominican Sisters"));
        hardQuestions.add(new QuestionHard("What year was the last graduation before it became Dominican-run?", "1973"));
        hardQuestions.add(new QuestionHard("Who is the current principal of Dominican College of Tarlac as of 2025?", "Sister Ma. Alelee M. Masanque"));
        hardQuestions.add(new QuestionHard("What SHS strand is for undecided students? (Write the acronym)", "GAS"));
        hardQuestions.add(new QuestionHard("What SHS strand is for students who want to study business? (Write the acronym)", "ABM"));
        hardQuestions.add(new QuestionHard("What strand in the TVL track is for hospitality and cooking careers?", "F&B"));
        hardQuestions.add(new QuestionHard("What program teaches IT, cybersecurity, and networks?", "Information Technology"));
        hardQuestions.add(new QuestionHard("What college program is for future police officers and investigators?", "Criminology"));
        hardQuestions.add(new QuestionHard("What word in the DCT logo means 'Wisdom'?", "Sapientia"));
        hardQuestions.add(new QuestionHard("Fill in the blank: “A God-loving educational community of servant leaders with passion for truth and compassion for _____.”", "humanity"));

    }

    private void studandprayHard() {
        hardQuestions = new ArrayList<>();
        hardQuestions.add(new QuestionHard("Who is the founder of the Order of Preachers?", "St. Dominic De Guzman"));
        hardQuestions.add(new QuestionHard("Where was St. Dominic de Guzman born?", "Caleruga, Spain"));
        hardQuestions.add(new QuestionHard("What year was St. Dominic de Guzman born?", "1170"));
        hardQuestions.add(new QuestionHard("Who were the parents of St. Dominic de Guzman?", "Felix de Guzman and Juana de Aza"));
        hardQuestions.add(new QuestionHard("Name the two siblings of St. Dominic de Guzman.", "Anthony and Mannes"));
        hardQuestions.add(new QuestionHard("What did Dominic's godmother perceive on his forehead at baptism?", "Star"));
        hardQuestions.add(new QuestionHard("At what age did Dominic attend the University of Palencia?", "14"));
        hardQuestions.add(new QuestionHard("What did Dominic sell to give alms to the poor?", "His precious books"));
        hardQuestions.add(new QuestionHard("Give one of the four subjects included in the Quadrivium that Dominic studied.", "Arithmetic"));
        hardQuestions.add(new QuestionHard("What false teaching did Dominic encounter during his journey in 1203?", "Albigensianism"));
        hardQuestions.add(new QuestionHard("What belief did Albigensianism hold regarding material things?", "Material things are evil"));
        hardQuestions.add(new QuestionHard("What was the name of the religious order approved by Pope Honorius III in 1216?", "The Order of the Preachers"));
        hardQuestions.add(new QuestionHard("Name one saint produced by the Order of Preachers.", "St. Thomas Aquinas"));
        hardQuestions.add(new QuestionHard("When did St. Dominic de Guzman die?", "August 6, 1221"));
        hardQuestions.add(new QuestionHard("When is the Feast day of Saint Dominic?", "August 8"));


    }

    private void introinstudentlifeHard() {
        hardQuestions = new ArrayList<>();
        hardQuestions.add(new QuestionHard("What is considered the most important and golden period of development?", "Student Life"));
        hardQuestions.add(new QuestionHard("Give one of the main academic tasks that students engage in.", "Going to classes"));
        hardQuestions.add(new QuestionHard("Give one of the key aspects of managing time as a student balancing schoolwork with activities.", "Sports"));
        hardQuestions.add(new QuestionHard("Give one of the benefits of studying in a group.", "Gain Knowledge"));
        hardQuestions.add(new QuestionHard("Why should students avoid comparing themselves to others?", "Everyone has their own abilities and capabilities"));
        hardQuestions.add(new QuestionHard("What is one common issue related to mental health that students face?", "Mental health issues"));
        hardQuestions.add(new QuestionHard("What is a common financial challenge faced by students?", "Financial constraints"));
        hardQuestions.add(new QuestionHard("What is a significant social challenge students might encounter?", "Social Issues"));
        hardQuestions.add(new QuestionHard("What is a common technological barrier for students?", "Technological Barriers"));
        hardQuestions.add(new QuestionHard("What is one activity students can do to reflect on their educational journey?", "Make a timeline of their education history"));
        hardQuestions.add(new QuestionHard("Why is time management important for students?", "It enables students to do more and better work in less time."));
        hardQuestions.add(new QuestionHard("What is an unforgettable experience students might have?", "Experiences of being rejected and forming friendship."));
        hardQuestions.add(new QuestionHard("What is the role of a favorite teacher in a student's life?", "A favorite teacher impacts their life significantly."));
        hardQuestions.add(new QuestionHard("Give one importance of learning academic knowledge in student life.", "Learn manners"));
        hardQuestions.add(new QuestionHard("What is one way to enhance classroom engagement?", "Pay attention to what teachers teach"));


    }

    private void EducationandHard() {
        hardQuestions = new ArrayList<>();
        hardQuestions.add(new QuestionHard("What is the process of teaching, or the imparting of knowledge, skills, values, beliefs, and habits?", "Education"));
        hardQuestions.add(new QuestionHard("What is the process of acquiring new or modifying existing knowledge, behaviors, skills, values, or preferences?", "Learning"));
        hardQuestions.add(new QuestionHard("What type of education is structured, follows a specific curriculum, and is typically received in schools and colleges?", "Formal Education"));
        hardQuestions.add(new QuestionHard("What type of education happens outside traditional classrooms and occurs naturally through life experiences?", "Informal Education"));
        hardQuestions.add(new QuestionHard("What kind of learning results from daily life activities, not organized or structured?", "Non-formal Education"));
        hardQuestions.add(new QuestionHard("Why is education considered one of the most powerful things in life?", "It allows us to find the meaning behind everything and helps improve lives in a massive way."));
        hardQuestions.add(new QuestionHard("What intelligence is associated with thinking in patterns, rhythms, and sounds?", "Musical Intelligence"));
        hardQuestions.add(new QuestionHard("Which type of intelligence involves being aware of one's own emotional states, feelings, and motivations?", "Intrapersonal Intelligence"));
        hardQuestions.add(new QuestionHard("What type of intelligence is associated with being in tune with nature and exploring the environment?", "Naturalistic Intelligence"));
        hardQuestions.add(new QuestionHard("Which intelligence is linked to using words well in writing and speaking?", "Verbal/Linguistic Intelligence"));
        hardQuestions.add(new QuestionHard("Which type of intelligence is strong in understanding and interacting with other people?", "Interpersonal Intelligence"));
        hardQuestions.add(new QuestionHard("Which intelligence is associated with visualizing things and spatial judgment?", "Spatial Intelligence"));
        hardQuestions.add(new QuestionHard("What intelligence is related to body movement, performing actions, and physical control?", "Bodily Kinesthetic Intelligence"));
        hardQuestions.add(new QuestionHard("Which intelligence excels in reasoning, pattern recognition, and problem analysis?", "Logical-Mathematical Intelligence"));
        hardQuestions.add(new QuestionHard("What type of intelligence allows one to explore deep questions about life and existence?", "Existential Intelligence"));


    }

    private void impEducationandHard() {
        hardQuestions = new ArrayList<>();
        hardQuestions.add(new QuestionHard("What is anything that causes stress or triggers a response of mental, emotional, or physical tension?", "STRESSOR"));
        hardQuestions.add(new QuestionHard("What is how your body and mind react when you're feeling overwhelmed, pressured, or worried about something?", "STRESS"));
        hardQuestions.add(new QuestionHard("What type of stress is beneficial and motivating, helping individuals feel excited and energized?", "EUSTRESS"));
        hardQuestions.add(new QuestionHard("What type of stress is harmful and debilitating, leading to anxiety, health problems, and a decrease in well-being?", "DISTRESS"));
        hardQuestions.add(new QuestionHard("What stressors often arise from an individual's daily life and emotional well-being?", "PERSONAL STRESSOR"));
        hardQuestions.add(new QuestionHard("What stressors are common for students and stem from school or university pressures?", "ACADEMIC STRESSORS"));
        hardQuestions.add(new QuestionHard("What type of stress is work-related and may be due to demanding job responsibilities or lack of job security?", "PROFESSIONAL STRESS"));
        hardQuestions.add(new QuestionHard("What financial issue can lead to personal stress?", "Financial pressure"));
        hardQuestions.add(new QuestionHard("What environmental factor, especially in urban areas, contributes to stress?", "Noise pollution"));
        hardQuestions.add(new QuestionHard("What is a physical reaction to stress that causes soreness in the neck, shoulders, and back?", "Muscle tension or pain"));
        hardQuestions.add(new QuestionHard("What fear related to employment contributes to stress?", "Job insecurity"));
        hardQuestions.add(new QuestionHard("What decision-making difficulty is often caused by stress?", "Indecisiveness"));
        hardQuestions.add(new QuestionHard("What are examples of healthy lifestyle choices that reduce stress?", "Healthy Lifestyle Choices"));
        hardQuestions.add(new QuestionHard("What kind of connection with others helps provide relief and perspective during stressful times?", "Social Support"));
        hardQuestions.add(new QuestionHard("What imbalance between professional and personal life can lead to stress?", "Poor work-life balance"));

    }

    // Question display methods
// Add this helper method to check question length
    private boolean isLongQuestion(String questionText) {
        return questionText.length() > 50;
    }

    // Update the startTimer methods to use dynamic timing
    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        Question current = questions.get(currentIndex);
        long timePerQuestion = isLongQuestion(current.getText()) ? 40000 : 30000;

        countDownTimer = new CountDownTimer(timePerQuestion, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText((millisUntilFinished / 1000) + " secs");
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
                timerText.setText((millisUntilFinished / 1000) + " secs");
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
                timerText.setText((millisUntilFinished / 1000) + " secs");
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

        switch (difficulty) {
            case "Easy":

                headertext.setText("REVIEW ANSWERS");
                scoretext.setText("Score: " + score + "/" + 15);
                for (int i = 0; i < questions.size(); i++) {
                    Question q = questions.get(i);
                    Boolean userAnswer = i < userEasyAnswers.size() ? userEasyAnswers.get(i) : null;
                    boolean correctAnswer = q.isAnswerTrue();

                    reviewContent.append("<b>").append(i + 1).append("</b>: ")
                            .append(q.getText()).append("<br>");

                    if (userAnswer == null) {
                        reviewContent.append("<b>Your Answer: <font color=\"#FFC107\">No answer</font></b><br>");
                    } else if (userAnswer == correctAnswer) {
                        reviewContent.append("<b><font color=\"#00BA00\">1 Point</font></b><br>");
                    } else {
                        reviewContent.append("<b>Your Answer: <font color=\"#EE685C\">")
                                .append(userAnswer ? "True" : "False")
                                .append("</font></b><br>");
                    }

                    reviewContent.append("<b>Correct Answer: <font color=\"#00BA00\">")
                            .append(correctAnswer ? "True" : "False")
                            .append("</font></b><br><br>");

                }
                break;

            case "Medium":
                headertext.setText("REVIEW ANSWERS");
                scoretext.setText("Score: " + score + "/" + 15);
                for (int i = 0; i < mediumquestions.size(); i++) {
                    QuestionMed q = mediumquestions.get(i);
                    int correctIndex = q.getCorrectAnswerIndex();
                    Integer userAnswerIndex = i < userMediumAnswers.size() ? userMediumAnswers.get(i) : -1;
                    String correctAnswer = q.getOptions()[correctIndex];
                    String userAnswer = userAnswerIndex != -1 ? q.getOptions()[userAnswerIndex] : "No answer";

                    reviewContent.append("<b>").append(i + 1).append("</b>: ")
                            .append(q.getText()).append("<br>");

                    if (userAnswerIndex == -1) {
                        reviewContent.append("<b>Your Answer: <font color=\"#FFC107\">No answer</font></b><br>");
                    } else if (userAnswerIndex == correctIndex) {
                        reviewContent.append("<b><font color=\"#00BA00\">1 Point</font></b><br>");
                    } else {
                        reviewContent.append("<b>Your Answer: <font color=\"#EE685C\">")
                                .append(userAnswer)
                                .append("</font></b><br>");
                    }

                    reviewContent.append("<b>Correct Answer: <font color=\"#00BA00\">")
                            .append(correctAnswer)
                            .append("</font></b><br><br>");
                }
                break;

            case "Hard":
                headertext.setText("REVIEW ANSWERS");
                scoretext.setText("Score: " + score + "/" + 15);
                for (int i = 0; i < hardQuestions.size(); i++) {
                    QuestionHard q = hardQuestions.get(i);
                    String correctAnswer = q.getAnswer();
                    String userAnswer = i < userHardAnswers.size() ? userHardAnswers.get(i) : "No answer";

                    reviewContent.append("<b>").append(i + 1).append("</b>: ")
                            .append(q.getText()).append("<br>");

                    if (userAnswer == null || userAnswer.equals("")) {
                        reviewContent.append("<b>Your Answer: <font color=\"#FFC107\">No answer</font></b><br>");
                    } else if (userAnswer.equals(correctAnswer)) {
                        reviewContent.append("<b><font color=\"#00BA00\">1 Point</font></b><br>");
                    } else {
                        reviewContent.append("<b>Your Answer: <font color=\"#EE685C\">")
                                .append(userAnswer)
                                .append("</font></b><br>");
                    }

                    reviewContent.append("<b>Correct Answer: <font color=\"#00BA00\">")
                            .append(correctAnswer)
                            .append("</font></b><br><br>");
                }
                break;
        }

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

