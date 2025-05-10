package com.draker.funnynumbers;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements MainView {

    private TextView tvQuestion;
    private TextView tvScore;
    private TextView tvTimer;
    private TextView tvResult;
    private TextInputEditText etAnswer;
    private TextInputLayout tilAnswer;
    private Button btnSubmit;
    private Button btnNewGame;
    private CardView cardQuestion;
    private ImageView ivTimer;
    
    private QuizPresenter presenter;
    private Animation fadeInAnimation;
    private Animation slideUpAnimation;
    private Animation shakeAnimation;
    private Animation pulseAnimation;
    private Animation rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // Настройка отступов для Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        // Загрузка анимаций
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        
        // Инициализация представления
        initViews();
        
        // Создание презентера и его привязка к представлению
        presenter = new QuizPresenter(this);
        
        // Настройка обработчиков событий
        setupEventListeners();
    }
    
    private void initViews() {
        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        tvTimer = findViewById(R.id.tvTimer);
        tvResult = findViewById(R.id.tvResult);
        etAnswer = findViewById(R.id.etAnswer);
        tilAnswer = findViewById(R.id.tilAnswer);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnNewGame = findViewById(R.id.btnNewGame);
        cardQuestion = findViewById(R.id.cardQuestion);
        ivTimer = findViewById(R.id.ivTimer);
        
        // Запускаем вращение иконки таймера
        ivTimer.startAnimation(rotateAnimation);
        
        // Начальное состояние кнопок
        btnSubmit.setEnabled(false);
        etAnswer.setEnabled(false);
    }
    
    private void setupEventListeners() {
        btnNewGame.setOnClickListener(v -> {
            startGameWithAnimations();
        });
        
        btnSubmit.setOnClickListener(v -> {
            if (etAnswer.getText() == null || etAnswer.getText().toString().isEmpty()) {
                tilAnswer.setError(getString(R.string.enter_answer_prompt));
                return;
            }
            
            tilAnswer.setError(null); // Сбрасываем ошибку
            v.startAnimation(pulseAnimation); // Анимация нажатия
            presenter.checkAnswer(etAnswer.getText().toString());
        });
    }
    
    private void startGameWithAnimations() {
        presenter.startNewGame();
        btnSubmit.setEnabled(true);
        etAnswer.setEnabled(true);
        tvResult.setText("");
        tilAnswer.setError(null);
        
        // Применяем анимации
        cardQuestion.startAnimation(fadeInAnimation);
        tilAnswer.startAnimation(slideUpAnimation);
        btnSubmit.startAnimation(slideUpAnimation);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // Остановка игры при переходе в фоновый режим
        if (presenter.isGameActive()) {
            presenter.stopGame();
        }
    }

    // Методы интерфейса MainView
    @Override
    public void showQuestion(String question) {
        tvQuestion.setText(question);
        cardQuestion.startAnimation(fadeInAnimation);
    }

    @Override
    public void updateScore(int score) {
        tvScore.setText(getString(R.string.score_format, score));
    }

    @Override
    public void showAnswerResult(boolean isCorrect, String correctAnswer) {
        if (isCorrect) {
            tvResult.setTextColor(getResources().getColor(R.color.correct_answer, getTheme()));
            tvResult.setText(getString(R.string.correct_answer));
        } else {
            tvResult.setTextColor(getResources().getColor(R.color.wrong_answer, getTheme()));
            tvResult.setText(getString(R.string.wrong_answer, correctAnswer));
            cardQuestion.startAnimation(shakeAnimation);
        }
        
        tvResult.startAnimation(fadeInAnimation);
    }

    @Override
    public void updateTimer(int secondsLeft) {
        tvTimer.setText(getString(R.string.timer_format, secondsLeft));
        
        // Меняем цвет таймера когда мало времени
        if (secondsLeft <= 10) {
            tvTimer.setTextColor(getResources().getColor(R.color.timer_warning, getTheme()));
        } else {
            tvTimer.setTextColor(getResources().getColor(R.color.text_secondary, getTheme()));
        }
    }

    @Override
    public void showGameOver(int finalScore) {
        // Отключаем поле ввода и кнопку проверки
        btnSubmit.setEnabled(false);
        etAnswer.setEnabled(false);
        
        // Создаем оценку для финального счета
        String rating;
        if (finalScore >= 100) {
            rating = "Превосходно! Вы математический гений! 🏆";
        } else if (finalScore >= 70) {
            rating = "Отлично! Вы знаток чисел! 🥇";
        } else if (finalScore >= 40) {
            rating = "Хорошо! Вы неплохо разбираетесь в математике! 🥈";
        } else if (finalScore >= 20) {
            rating = "Неплохо! Есть к чему стремиться! 🥉";
        } else {
            rating = "Попробуйте еще раз! Практика ведет к совершенству! 💪";
        }
        
        // Показываем диалог с результатами
        AlertDialog.Builder builder = new AlertDialog.Builder(this, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog);
        builder.setTitle(R.string.game_over_title)
            .setMessage(getString(R.string.game_over_message, finalScore) + "\n\n" + rating)
            .setPositiveButton(R.string.new_game, (dialog, which) -> {
                startGameWithAnimations();
            })
            .setNegativeButton(R.string.exit, (dialog, which) -> dialog.dismiss())
            .setCancelable(false)
            .show();
    }

    @Override
    public void clearAnswerField() {
        etAnswer.setText("");
    }
}
