package edu.anderson.rodneyClicker
val verifyComplete = Array(10) {0}
var completedAchievements = ""

fun checkAchievements(totalRD:Int, totalClicks:Int, rodneys:Int ):String{
    var achievementFound = ""
    if(totalRD>999){
        achievementFound = displayAchievements("Making Money", "make it to a thousand Raven Dollars",0)
    }
    if(totalRD>99){
        achievementFound = displayAchievements("Millionaire", "collect a million Raven Dollars",1)
    }
    if(totalRD>999999999){
        achievementFound = displayAchievements("1%", "get a billion Raven Dollars",2)
    }
    if(totalClicks>99) {
        achievementFound = displayAchievements("Sore Thumb", "click 100 times",3)
    }
    if(totalClicks>4999) {
        achievementFound = displayAchievements("Why would you do this?", "click 5,000 times",4)
    }
    if(rodneys>99){
        achievementFound = displayAchievements("That's a lot of birds", "gain 100 rodneys",5)
    }
    if(totalClicks>0){
        achievementFound = displayAchievements("What's this?", "click the raven",6)
    }

    return achievementFound
}

fun displayAchievements(title:String, description:String, aNum:Int): String {
    val text = "\n$title:$description "
    return if(!completedAchievements.contains(text)) {
        if(verifyComplete[aNum] == 5){
            completedAchievements += text
        }
        verifyComplete[aNum]++
        text
    }
    else {
        ""
    }
}
