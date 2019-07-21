package ru.skillbranch.devintensive.models

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
        if (question.validQuestion(answer).length != 0) return "${question.validQuestion(answer)}\n${question.question}" to status.color
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


        }
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
            override fun validQuestion(s: String): String {
                val reg = """[A-ZА-ЯЁ]\S*"""
                var regex = Regex(reg)
                val matchRez = regex.matches(s)
                if (matchRez) return ""
                else return "Имя должно начинаться с заглавной буквы"
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validQuestion(s: String): String {
                val reg = """[a-zа-яё]\S*"""
                var regex = Regex(reg)
                val matchRez = regex.matches(s)
                if (matchRez) return ""
                else return "Профессия должна начинаться со строчной буквы"
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun validQuestion(s: String): String {
                val reg = """\D*"""
                var regex = Regex(reg)
                val matchRez = regex.matches(s)
                if (matchRez) return ""
                else return "Материал не должен содержать цифр"
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validQuestion(s: String): String {
                val reg = """\d*"""
                var regex = Regex(reg)
                val matchRez = regex.matches(s)
                if (matchRez) return ""
                else return "Год моего рождения должен содержать только цифры"
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validQuestion(s: String): String {
                val reg = """\d{7}"""
                var regex = Regex(reg)
                val matchRez = regex.matches(s)
                if (matchRez) return ""
                else return "Серийный номер содержит только цифры, и их 7"
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validQuestion(s: String): String {
                return ""
            }
        };

        abstract fun nextQuestion(): Question
        abstract fun validQuestion(s: String): String
    }
}