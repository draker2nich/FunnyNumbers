package com.draker.funnynumbers;

import android.os.CountDownTimer;

/**
 * Презентер для управления логикой викторины
 */
public class QuizPresenter {
    private MainView view;
    private QuizQuestion currentQuestion;
    private int score = 0;
    private CountDownTimer timer;
    private static final int GAME_TIME_SECONDS = 60;
    private int remainingSeconds = GAME_TIME_SECONDS;
    private boolean isGameActive = false;
    
    public QuizPresenter(MainView view) {
        this.view = view;
    }
    
    /**
     * Начать новую игру
     */
    public void startNewGame() {
        score = 0;
        remainingSeconds = GAME_TIME_SECONDS;
        isGameActive = true;
        
        view.updateScore(score);
        generateNewQuestion();
        startTimer();
    }
    
    /**
     * Обработать ответ пользователя
     * @param userAnswer ответ, введенный пользователем
     */
    public void checkAnswer(String userAnswer) {
        if (!isGameActive || currentQuestion == null) {
            return;
        }
        
        int userAnswerValue;
        try {
            userAnswerValue = Integer.parseInt(userAnswer);
        } catch (NumberFormatException e) {
            userAnswerValue = -9999; // Заведомо неверный ответ
        }
        
        boolean isCorrect = currentQuestion.checkAnswer(userAnswerValue);
        
        if (isCorrect) {
            score += 10; // Добавляем очки за правильный ответ
            view.updateScore(score);
        }
        
        view.showAnswerResult(isCorrect, String.valueOf(currentQuestion.getCorrectAnswer()));
        view.clearAnswerField();
        
        // Генерируем новый вопрос через небольшую задержку
        new android.os.Handler().postDelayed(this::generateNewQuestion, 1500);
    }
    
    /**
     * Генерировать новый вопрос
     */
    private void generateNewQuestion() {
        if (!isGameActive) {
            return;
        }
        
        currentQuestion = NumberGenerator.generateQuestion();
        view.showQuestion(currentQuestion.getQuestionText());
    }
    
    /**
     * Запустить таймер обратного отсчета
     */
    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        
        timer = new CountDownTimer(remainingSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingSeconds = (int) (millisUntilFinished / 1000);
                view.updateTimer(remainingSeconds);
            }
            
            @Override
            public void onFinish() {
                endGame();
            }
        }.start();
    }
    
    /**
     * Завершить игру
     */
    private void endGame() {
        isGameActive = false;
        if (timer != null) {
            timer.cancel();
        }
        view.showGameOver(score);
    }
    
    /**
     * Прервать текущую игру
     */
    public void stopGame() {
        if (timer != null) {
            timer.cancel();
        }
        isGameActive = false;
    }
    
    /**
     * Проверить, активна ли игра
     */
    public boolean isGameActive() {
        return isGameActive;
    }
}
