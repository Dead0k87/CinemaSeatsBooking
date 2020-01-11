# CinemaSeatsBooking

### Task:

Initially, all seats are free, total seats 80. 
Each user can book one seat at a time through the application. 
The application should reserve the available seat from 1 to 80 given by user input if empty. 
The application should be thread-safe (i.e. many users can book at the same time, but the same seat can't be booked by more than one user)

### Solution:

Program is using ServerSocket and Socket connection which is processed in a separate Thread.