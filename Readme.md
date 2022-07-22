
# README

### Requirement & Assumption
---
- Reads, processes, and extracts information from a standard format file as required. 
- Inpu files are assumed to be machine-generated and clean.
- As sample file implies, input does not contains record with same time and have order in time. While considering it's common in real world that records may be delivered more than once and misordered, basic deduplication and ordering is performed when analyze the file.



### Solution 
---
* <em> Two solutions, in Java & Python respectively, are provided in the repository. They are not my main programing language but good to explore & learn, so any suggestion will be welcome :wink: </em>
* Java
  * It is implemented following OOP approach and googling syntax as coding. Class and unit test code are provided.
  * To run it, navigate inside Main.java, run or alter input path to see the result.
  * Execution result:
   <img width="276" alt="Screen Shot 2022-07-22 at 12 30 40 pm" src="https://user-images.githubusercontent.com/37531758/180350361-6e32800f-1b9a-4c77-9351-a58fd479f586.png">

  
* Python
  * It uses pandas to analyze the file which comes in handy. It is an experimental implementation so only class code is provided.
  * To run it, navigate inside Main.py, run or alter input path to see the result.
  * Execution result: 
  <img width="276" alt="Screen Shot 2022-07-22 at 12 30 40 pm" src="https://user-images.githubusercontent.com/37531758/180366605-4cbab11a-309f-4665-a98d-ad6e2554fe81.png">

