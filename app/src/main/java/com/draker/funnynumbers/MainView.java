package com.draker.funnynumbers;

/**
 * Интерфейс представления для главного экрана
 */
public interface MainView {
    /**
     * Отображает новый вопрос викторины
     * @param question текст вопроса
     */
    void showQuestion(String question);
    
    /**
     * Обновляет счет пользователя
     * @param score текущий счет
     */
    void updateScore(int score);
    
    /**
     * Показывает результат ответа (правильно/неправильно)
     * @param isCorrect результат проверки
     * @param correctAnswer правильный ответ
     */
    void showAnswerResult(boolean isCorrect, String correctAnswer);
    
    /**
     * Обновляет таймер обратного отсчета
     * @param secondsLeft оставшееся время в секундах
     */
    void updateTimer(int secondsLeft);
    
    /**
     * Показывает окончание игры
     * @param finalScore финальный счет
     */
    void showGameOver(int finalScore);
    
    /**
     * Очищает поле ввода ответа
     */
    void clearAnswerField();
}
