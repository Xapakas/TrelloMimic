# csc360-sprint1

**Corrections from past sprints:**

Since my only grade deduction was the testing for sprint 2, I went back and reworked the whole thing. It now, in addition to testing the four major methods (authenticate user, create board, get board, and update board), checks that every possible detail of a board is saved and loaded properly.
You can find the updated sprint 2 testing in trello/src/test/java/serverClient/TrelloServerTest.java

I also added colors to the views to avoid the "programmer gray" you talked about, using a generated palette from coolors.co. The affected views were the login view, the user view, the board view, the list view, and the card view.
You can find the updated views in trello/src/main/java/views

---

To run:
1. Go to /trello/target/classes and run GitBash
2. Run rmiregistry.exe
3. Run /trello/src/main/java/serverClient/TrelloServer.java
4. Run /trello/src/main/java/main/Main.java
5. Have fun!

To test:
* Follow steps 1-3
* Run /trello/src/test/java/views/ViewTest.java
* Follow step 5