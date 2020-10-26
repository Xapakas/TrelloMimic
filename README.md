# csc360-sprint1

There are four major test files: one for User, one for Board, one for BList, and one for Card. Other minor classes such as Component and Label are XML-tested in the Card test file, and HasMembersSet and HasMembersList are tested in the Board test file.

What doesn't work: Sad news :( It doesn't store to XML with 100% accuracy. The culprit seems to be when classes have HasMembersList or HasMembersSet as attributes. It doesn't store their contents properly so it fails the equality test. I hope I can meet with you tomorrow to work on fixing this issue.