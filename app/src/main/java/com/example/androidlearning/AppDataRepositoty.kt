package com.example.androidlearning

class AppDataRepositoty {

    fun getData(): List<AppData>{
        val AppList = listOf(
            AppData("Game 1", "Strategy", "Description 1, pretty small", "https://picsum.photos/200/200"),
            AppData("Game 2", "Economical", "Description 2, pretty big pretty" +
                    " big pretty big pretty " + " big pretty big pretty " +
                    "big pretty big pretty big", "https://picsum.photos/200/200"),
            AppData("Game 3", "Adventure", "Decription 3", "https://picsum.photos/200/200"),
            AppData("Game 4", "Action", "Description 4", "https://picsum.photos/200/200"),
            AppData("Game 5", "Strategy", null, "https://picsum.photos/200/200"),
            AppData("Game 6", null, "Description 6", "https://picsum.photos/200/200"),
            AppData("Game 7", "Strategy", "Description 7", "https://picsum.photos/200/200"),
            AppData("Game 8", "Strategy", "Description 8", "https://picsum.photos/200/200")
        )

        return AppList
    }

}