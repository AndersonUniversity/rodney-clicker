package edu.anderson.rodneyClicker
val verifyComplete = Array(10) { 0 }
var completedAchievements = ""
var achievementCount = 0

fun checkAchievements(totalRD: Long, totalClicks: Int, rodneys: Int, helios: Int, flames: Int, koontz: Int, tandy: Int): String {
    var achievementFound = ""

    if (totalRD> 999 && verifyComplete[0] <5) {
        achievementFound = displayAchievements("Making Money", "make it to a thousand Raven Dollars", 0)
    }
    if (totalRD> 999999 && verifyComplete[1] <5 && achievementFound == "") {
        achievementFound = displayAchievements("Millionaire", "collect a million Raven Dollars", 1)
    }
    if (totalRD> 999999999 && verifyComplete[2] <5 && achievementFound == "") {
        achievementFound = displayAchievements("1%", "get a billion Raven Dollars", 2)
    }
    if (totalClicks> 99 && verifyComplete[3] <5 && achievementFound == "") {
        achievementFound = displayAchievements("Sore Thumb", "click 100 times", 3)
    }
    if (totalClicks> 4999 && verifyComplete[4] <5 && achievementFound == "") {
        achievementFound = displayAchievements("Why would you do this?", "click 5,000 times", 4)
    }
    if (rodneys> 99 && verifyComplete[5] <5 && achievementFound == "") {
        achievementFound = displayAchievements("That's a lot of birds", "gain 100 rodneys", 5)
    }
    if (totalClicks> 0 && verifyComplete[6] <5 && achievementFound == "") {
        achievementFound = displayAchievements("What's this?", "click the raven", 6)
    }
    if (rodneys >= 1 && helios >= 1 && flames >= 1 && koontz >= 1 && tandy >= 1 && verifyComplete[7] <5 && achievementFound == "") {
        achievementFound = displayAchievements("Gotta catch 'em all", "get every upgrade", 7)
    }
    if (flames > 99 && verifyComplete[8] <5 && achievementFound == "") {
        achievementFound = displayAchievements("Arson", "set 100 eternal flames", 8)
    }
    if (!verifyComplete.contains(0) && verifyComplete[9] <5 && achievementFound == "") {
        achievementFound = displayAchievements("Completionist", "get every achievement", 9)
    }

    return achievementFound
}

fun displayAchievements(title: String, description: String, aNum: Int): String {
    var text = "\n$title\n$description"
    if (completedAchievements.contains(text)) {
        text = ""
    } else {
        if (verifyComplete[aNum] == 4) {
            completedAchievements += text
        }
    }
    verifyComplete[aNum]++
    return text
}
