package ru.skillbranch.devintensive.models

import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*
import java.util.regex.Matcher

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String {
        return when (question) {
            Question.NAME -> Question.NAME.question
            Question.PROFESSION -> Question.PROFESSION.question
            Question.MATERIAL -> Question.MATERIAL.question
            Question.BDAY -> Question.BDAY.question
            Question.SERIAL -> Question.SERIAL.question
            Question.IDLE -> Question.IDLE.question
        }
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {

        try {
            if (question.validQuestion(answer)) {
                question = question.nextQuestion()
                return "Отлично - ты справился\n${question.question}" to status.color
            } else {
                status=status.nextStatus()
                if (status == Status.NORMAL) {
                    question = Question.NAME
                    return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                }
                return "Это неправильный ответ\n${question.question}" to status.color
            }

        } catch (e: Exception) {
            return "${e.message!!}\n${question.question}" to status.color
        }
        /* if (question.validQuestion(answer).length != 0) return "${question.validQuestion(answer)}\n${question.question}" to status.color
         return if (question == Question.IDLE) {
             question.question to status.color
         } else if (question.answers.contains(answer.toLowerCase())) {
             question = question.nextQuestion()
             "Отлично - ты справился\n${question.question}" to status.color
         } else {
             status = status.nextStatus()
             if (status != Status.NORMAL) {
                 "Это неправильный ответ\n${question.question}" to status.color
             } else {
                 question = Question.NAME
                 "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
             }


         }*/

    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {

        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validQuestion(s: String): Boolean {
                val reg = """[A-ZА-ЯЁ]\S*"""
                val regex = Regex(reg)
                val matchRez = regex.matches(s)
                if (!matchRez) throw IllegalArgumentException("Имя должно начинаться с заглавной буквы")

                return answers.contains(s.toLowerCase(Locale.ROOT))

            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validQuestion(s: String): Boolean {
                val reg = """[a-zа-яё]\S*"""
                val regex = Regex(reg)
                val matchRez = regex.matches(s)
                if (!matchRez) throw IllegalArgumentException("Профессия должна начинаться со строчной буквы")
                return answers.contains(s.toLowerCase(Locale.ROOT))
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun validQuestion(s: String): Boolean {
                val reg = """\D*"""
                val regex = Regex(reg)
                val matchRez = regex.matches(s)
                if (!matchRez) throw IllegalArgumentException("Материал не должен содержать цифр")
                return answers.contains(s.toLowerCase(Locale.ROOT))
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validQuestion(s: String): Boolean {
                val reg = """\d*"""
                val regex = Regex(reg)
                val matchRez = regex.matches(s)
                if (!matchRez) throw IllegalArgumentException("Год моего рождения должен содержать только цифры")
                return answers.contains(s.toLowerCase(Locale.ROOT))
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validQuestion(s: String): Boolean {
                val reg = """\d{7}"""
                val regex = Regex(reg)
                val matchRez = regex.matches(s)
                if (!matchRez) throw IllegalArgumentException("Серийный номер содержит только цифры, и их 7")
                return answers.contains(s.toLowerCase(Locale.ROOT))
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validQuestion(s: String): Boolean {
                return true
            }
        };

        abstract fun nextQuestion(): Question
        abstract fun validQuestion(s: String): Boolean
    }
}