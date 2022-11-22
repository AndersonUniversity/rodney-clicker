package edu.anderson.rodneyClicker
val verifyComplete = Array(10) {0}
val completedAchievements = mutableListOf<String>()

fun checkAchievements(totalRD:Int, totalClicks:Int, rodneys:Int ):String{
    var achievementFound = ""
    var waitCount = 10
    if(totalRD>999 && verifyComplete[0]<=waitCount){
        achievementFound = displayAchievements("Making Money", "make it to a thousand Raven Dollars",completedAchievements,0)
    }
    if(totalRD>999999 && verifyComplete[1]<=waitCount){
        achievementFound = displayAchievements("Millionaire", "collect a million Raven Dollars",completedAchievements,1)
    }
    if(totalRD>999999999 && verifyComplete[2]<=waitCount){
        achievementFound = displayAchievements("1%", "get a billion Raven Dollars",completedAchievements,2)
    }
    if(totalClicks>99 && verifyComplete[3]<=waitCount) {
        achievementFound = displayAchievements("Sore Thumb", "click 100 times",completedAchievements,3)
    }
    if(totalClicks>4999 && verifyComplete[4]<=waitCount) {
        achievementFound = displayAchievements("Why would you do this?", "click 5,000 times",completedAchievements,4)
    }
    if(rodneys>99 && verifyComplete[5]<=waitCount){
        achievementFound = displayAchievements("That's a lot of birds", "gain 100 rodneys",completedAchievements,5)
    }
    if(totalClicks>0 && verifyComplete[6]<=waitCount){
        achievementFound = displayAchievements("What's this?", "click the raven",completedAchievements,6)
    }

    return achievementFound
}

fun displayAchievements(title:String, description:String,completedAchievements:MutableList<String>,aNum:Int):String{
    return if(completedAchievements.contains(title)){
        ""
    }
    else {
        if(verifyComplete[aNum]==10) {
            completedAchievements.add(title)
        }
        verifyComplete[aNum]++
        "\n$title\n$description"
    }
}
