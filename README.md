# Books

### The project
Tech: Kotlin (JDK17), Springboot

This app can be run locally and allows a user to upload and view their reading habits. It is a WIP.

### How to use (CLI)
* Start the app: `./gradlew bootRun`
* Go to `http://localhost:8080/` in your browser to see the books currently in memory.
* To add some, run the add script: `./addBooks.sh`
* To delete books: `./deleteAll.sh`
* To import a csv goodreads export file, go to `http://localhost:8080/import-demo`

Note: If getting permission issues running the scripts, use `chmod +x <script_name.sh>`

Coming soon: You can export a copy of your [goodreads history](https://www.goodreads.com/review/import) and import that using `./import.sh <$pathTofile/filename.csv>`

### Future Roadmap

There are fantastic projects currently in motion to provide alternatives to Amazon-owned sites that are both ethically and technically superior - in particular, I would recommend [Oku](https://oku.club/)

This project is for my own learning. My next goals with this project are to 
* host this service
* separate and host a db
* provide a frontend ui

*************
#### Git note
After managing to accidentally remove a few weeks of commit history, going to be much more careful with `Fatal: refusing to merge unrelated histories ` errors/warnings from now on... 
Potential alternative: `
git pull myapp master –allow-unrelated-histories`