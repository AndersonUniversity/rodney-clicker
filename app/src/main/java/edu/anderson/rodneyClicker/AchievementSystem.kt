package edu.anderson.rodneyClicker
val verifyComplete = Array(5) {0}

fun checkAchievements(totalRD:Int, totalClicks:Int ):String{
    var achievementFound = ""
    val completedAchievements = mutableListOf<String>()
    if(totalRD>999 && verifyComplete[0]<=5){
        verifyComplete[0] ++
       achievementFound = displayAchievements("Making Money", "Make it to a thousand Raven Dollars",completedAchievements)
        if(totalRD>999999){
            achievementFound = displayAchievements("Millionaire", "Collect a million Raven Dollars",completedAchievements)
            if(totalRD>999999999){

                achievementFound = displayAchievements("1%", "Get a billion Raven Dollars",completedAchievements)
            }
        }
    }
    if(totalClicks>99 && verifyComplete[1]<=5) {
        achievementFound = displayAchievements("Sore Thumb", "click 100 times",completedAchievements)
        verifyComplete[1] ++

    }
    if(totalClicks>9999 && verifyComplete[2]<=5) {
        verifyComplete[2] ++
        achievementFound = displayAchievements("Why would you do this?", "click 10,000 times",completedAchievements)
    }

    return achievementFound
}

fun displayAchievements(title:String, description:String,completedAchievements:MutableList<String>):String{
    return if(completedAchievements.contains(title)){
        ""
    }
    else {
        completedAchievements.add(title)
        "$title\n$description"
    }
}
