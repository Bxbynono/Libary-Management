Contents
Introduction	3
Problem statement	3
Aim	3
Objectives	3
Functional Requirements	3
Class Diagram	6
Results	6
Conclusion	12
GitHub link	13
Class Diagram link	13







 
Introduction 
A software program called a Library Management System (LMS) is made to help run and oversee a library. Numerous library tasks, including inventory management, circulation, and cataloging, are automated by this system. It facilitates the management of user accounts, the precise recording of books, and the expediency of the loan and return procedures. A Library Management System (LMS) makes use of digital tools to improve the efficacy and efficiency of library services. It also makes sure that customers can easily access resources, which lessens the workload of library staff.
Problem statement
Efficient resource management is a daunting task for traditional libraries. Manual procedures are labor-intensive and prone to mistakes when it comes to handling user information, tracking checked-out goods, and classifying books. Furthermore, it is challenging to give users up-to-date information regarding the availability of books due to the absence of an integrated system, which aggravates users. Misplaced books and faulty records are among the inventory management problems that libraries face. These inefficiencies show how much a complete library management system is needed to enhance both user experience and operational effectiveness.
Aim 
Our project's aim is to leverage design patterns and a strong foundation to create an extensive and user-friendly online library management platform in Java.
Objectives 
●	 Enhance User Experience: Provide an intuitive interface for users to search for and borrow books, check their account status, and receive notifications.
●	Improve Resource Management: Ensure accurate tracking of books and other resources, reducing the chances of loss or misplacement.
●	Real-time Information Access: Enable real-time updates on book availability, due dates, and overdue notifications.


Functional Requirements 
Librarian
1.	Login: The librarian can log into the system using their credentials.

2.	Approve or Reject Book Requests: The librarian can review book reservation requests from users and either approve or reject them.


Users
1.	Reserve Books: Users can browse the library catalog and reserve books they wish to borrow.

2.	Sign Up: New users can create an account to use the system.


3.	Login: Existing users can log into the system using their credentials.

Design Patterns
The following design patterns have been used to implement the functionalities of the LMS:

1. Chain of Responsibility (COR) Pattern
Used for: Login and Sign Up
	The COR pattern is employed to handle login and sign-up processes. It allows a request to be passed along a chain of handlers, where each handler processes the request or passes it to the next handler in the chain.
	Login Process: The login request is passed through a chain of handlers that validate user credentials, check account status, and perform necessary security checks.
	Sign-Up Process: The sign-up request is processed by a chain of handlers that validate the input data, check for existing accounts, and create a new user account.

2. Command Pattern
Used for: Approving or Rejecting Book Requests

	The Command pattern encapsulates a request as an object, thereby allowing for parameterization of clients with queues, requests, and operations. This pattern is used to handle approval and rejection of book requests.
	Approve Request: The command to approve a book request is created and executed.
	Reject Request: The command to reject a book request is created and executed.

3. Observer Pattern
Used for: Reserving Books
	The Observer pattern is used to create a subscription mechanism to allow multiple objects to listen to and react to events or changes in another object. This pattern is applied to the book reservation system.
	Reserve Book: When a user reserves a book, observers (such as the library catalog and notification system) are notified of the reservation.

4. Singleton Pattern
Used for: Database Connection
	The Singleton pattern ensures that a class has only one instance and provides a global point of access to it. This pattern is used to manage the database connection.
	Database Connection: A single instance of the database connection is created and shared across the system to ensure efficient resource usage and consistent access.

5. State Pattern
Used for: Accept Status, Booking Status, and Pending Status
	The State pattern allows an object to alter its behavior when its internal state changes. This pattern is used to manage the various statuses of book requests and reservations.
	Accept Status: Represents the state of a request that has been approved.
	Booking Status: Represents the state of a book that has been reserved by a user.
	Pending Status: Represents the state of a request that is awaiting approval or rejection.

Technical Requirements
Backend:  Java.
Database: Implement a relational database (e.g., MySQL) to manage books and user data.

 
Class Diagram
 

Results 
User signup.
 
User Login.
 

User Home page.
 

User book request.
 

Admin Login.
 
Admin approve and reject.
 
Conclusion
The Library Management System (LMS) is a robust, flexible, and efficient platform that leverages various design patterns to ensure modularity, maintainability, and scalability. The Chain of Responsibility (COR) pattern streamlines the login and sign-up processes by passing requests through a series of handlers, enabling flexible and scalable user authentication mechanisms. The Command pattern decouples the approval and rejection of book reservations by encapsulating these requests as objects, simplifying implementation and enhancing system flexibility. The Observer pattern keeps the reservation system synchronized by notifying all relevant components of changes in book reservation statuses, ensuring responsive and up-to-date operations. The Singleton pattern manages the database connection by ensuring a single, consistent point of access, optimizing resource usage and guaranteeing thread-safe database operations. Finally, the State pattern dynamically manages book request statuses, encapsulating status-related behaviors within state objects to provide a clear and manageable structure for state transitions. This strategic use of design patterns not only simplifies complex feature implementation but also ensures that the LMS can evolve and scale to meet future needs, resulting in a user-friendly and efficient library management solution.
GitHub link
https://github.com/Xevia28/Group6_CodeKicks
.
Class Diagram link
https://www.plantuml.com/plantuml/uml/nLN1RXen4BtFL_Z6Hb7o0QkgIaeFhLGr5IWVOBm3k3NsnfwbGMt_lUFrEel5aj9Jk81dtZo_DnwRkq3IKxSp03Enr7PZa9mLhIH2RyNQUT58aYiPK2XdBIhIpe8oCWJnSSZCNnBY5mZ-t6ZBl5PXFO59YDDuTTLdhZT8duVowkeimVLJX_vGXITJYoLvLixO1OREq189_aG_yuT5uSIuZRP2saO4lR6YQq5RJgwbGd7VqHOjQINZ5fyOPD0FTgu3qbTyfY5QRTDtFLqqs91Z3YlbiOaBQK8jvkE2DQ-SCoWjAqvTFKxdTdQ1BSWTPgifsZBwf_DDZeuKNzZectfOSdyVsxTHmEVkTZjcASvUjgrtU-wIu8N7xwWeBhZk1ozvc0A5SHmoHPQEpwWwwkTnxtGp6htlQV43qomIowMLYrx3VnNEnCCgeD-ZBwnvZC7ivjX5XcVEhcrY7oEi5d7h4rMFOKGdtjxRQoKnc0ULD1cSc4Uzyj8VCh4aacfRkK5T_Q8pS_Ue1Yz1MKTwVSYfC2rqneeaOT6rm94k26wvtE0EBOrzwykNVJvBJiDwUehVq3PizJBmlLBO4ZQNeHUf_Vz4pnXf-giJjheDCF4gtzoy7nztWCaB5q77b-eCwjtlszlYVNa5Lpu30BaiRvTl6HIu8WF3ZyHtzhOLiBDN2o3FISB5OIz3UL81OdMl9ES1ngb8sEBiJxB5OP-cozC5k6Eo-3Vy5m00

