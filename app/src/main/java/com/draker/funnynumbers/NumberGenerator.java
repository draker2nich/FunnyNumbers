package com.draker.funnynumbers;

import java.util.Random;

/**
 * Генератор числовых заданий для викторины
 */
public class NumberGenerator {
    private static final int MAX_NUMBER = 30;
    private static final int MIN_NUMBER = 1;
    private static final Random random = new Random();
    
    /**
     * Генерирует случайный вопрос викторины
     * @return объект QuizQuestion
     */
    public static QuizQuestion generateQuestion() {
        // Выбираем случайный тип вопроса
        QuizQuestion.QuestionType[] types = QuizQuestion.QuestionType.values();
        QuizQuestion.QuestionType type = types[random.nextInt(types.length)];
        
        switch (type) {
            case ADDITION:
                return generateAdditionQuestion();
            case SUBTRACTION:
                return generateSubtractionQuestion();
            case MULTIPLICATION:
                return generateMultiplicationQuestion();
            case DIVISION:
                return generateDivisionQuestion();
            case SQUARE:
                return generateSquareQuestion();
            case RANDOM_FACT:
                return generateRandomFactQuestion();
            default:
                return generateAdditionQuestion();
        }
    }
    
    private static QuizQuestion generateAdditionQuestion() {
        int a = random.nextInt(MAX_NUMBER) + MIN_NUMBER;
        int b = random.nextInt(MAX_NUMBER) + MIN_NUMBER;
        int answer = a + b;
        String question = "Сколько будет " + a + " + " + b + "?";
        
        return new QuizQuestion(question, answer, QuizQuestion.QuestionType.ADDITION);
    }
    
    private static QuizQuestion generateSubtractionQuestion() {
        int a = random.nextInt(MAX_NUMBER) + MIN_NUMBER;
        int b = random.nextInt(a) + 1; // b всегда меньше a, чтобы результат был положительным
        int answer = a - b;
        String question = "Сколько будет " + a + " - " + b + "?";
        
        return new QuizQuestion(question, answer, QuizQuestion.QuestionType.SUBTRACTION);
    }
    
    private static QuizQuestion generateMultiplicationQuestion() {
        int a = random.nextInt(10) + 1; // Используем меньший диапазон для умножения
        int b = random.nextInt(10) + 1;
        int answer = a * b;
        String question = "Сколько будет " + a + " × " + b + "?";
        
        return new QuizQuestion(question, answer, QuizQuestion.QuestionType.MULTIPLICATION);
    }
    
    private static QuizQuestion generateDivisionQuestion() {
        int b = random.nextInt(10) + 1;
        int a = b * (random.nextInt(10) + 1); // Убедимся, что деление будет без остатка
        int answer = a / b;
        String question = "Сколько будет " + a + " ÷ " + b + "?";
        
        return new QuizQuestion(question, answer, QuizQuestion.QuestionType.DIVISION);
    }
    
    private static QuizQuestion generateSquareQuestion() {
        int a = random.nextInt(10) + 1;
        int answer = a * a;
        String question = "Чему равен квадрат числа " + a + "?";
        
        return new QuizQuestion(question, answer, QuizQuestion.QuestionType.SQUARE);
    }
    
    private static QuizQuestion generateRandomFactQuestion() {
        // Набор интересных фактов о числах
        int factNumber = random.nextInt(5);
        int answer;
        String question;
        
        switch (factNumber) {
            case 0:
                answer = 7;
                question = "Сколько дней в неделе?";
                break;
            case 1:
                answer = 12;
                question = "Сколько месяцев в году?";
                break;
            case 2:
                answer = 60;
                question = "Сколько секунд в минуте?";
                break;
            case 3:
                answer = 24;
                question = "Сколько часов в сутках?";
                break;
            case 4:
                answer = 365;
                question = "Сколько дней в невисокосном году?";
                break;
            default:
                answer = 100;
                question = "Сколько сантиметров в метре?";
        }
        
        return new QuizQuestion(question, answer, QuizQuestion.QuestionType.RANDOM_FACT);
    }
}
