#  Dashboard-Java

This is a JavaFX application that provides a dashboard for managing clients and workers, tracking monthly income, and scheduling events in a calendar.
It's a part I developed when I was working on a paid Nursery Management Software project, which I built in collaboration with [Chakib Hadadi](https://github.com/schakibb/) .

The reason why I'm putting this as public repository is to help anyone who wants to use CalendarFX, since I didn't find a lot of projects with it.

*note:* the provided data is hard-coded (I cant provide the real data).
## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
### Prerequisites
- Java 11 or higher
- Running MongoDB local server
### Installing
1. Clone the repository
```bash
  git clone https://github.com/MohamedMouloudj/your-repo-name.git
```
2. Navigate into the cloned repository
```bash
  cd Dashboard
```
3. Build the project with Maven
```bash
  ./mvnw clean install
```
4. Run the application

   It maybe take some time to run
```bash
  ./mvnw.cmd exec:java
```
## Built With
- [JavaFX](https://openjfx.io/) - The GUI framework used
- [Maven](https://maven.apache.org/) - Dependency Management
- [CalendarFX](http://dlsc.com/products/calendarfx/) - Used for creating the calendar view
- [ControlsFX](http://fxexperience.com/controlsfx/) - Used for additional JavaFX controls
- [MongoDB](https://www.mongodb.com/) - Used for database management
## Authors
- Mohamed Mouloudj <mouloudj.mohamed.04@gmail.com> or <mouloudy656565@gmail.com>
