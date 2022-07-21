
# README

### Requirement & Assumption
---
- Reads, processes, and extracts information from a standard format file as required. 
- Inpu files are assumed to be mchine-generated and clean.
- Input files should not contains record with same time and have order in time (as requirement implies). While considering it's more common in real world that records may be delivered more than once and misordered, basic deduplication and ordering is performed when analyze the file.



### Solution 
---
* Java
  * It is implemented following OOP approach and googling syntax as coding. Class and unit test code are provided.
  * To run it, navigate inside Main.java, run or alter input to see the result.
  
* Python
  * It uses pandas to analyze the file which comes in handy. It is an experimental implementation so only class code is provided.
  * To run it, navigate inside Main.py, run or alter input to see the result.
