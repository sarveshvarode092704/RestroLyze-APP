# RestroLyze-APP
RestoLyze is a powerful machine learning-based Android application designed for analysts and senior executives of restaurant chains to analyze insights and implement data-driven strategies for growth and development.
RESTROLYZE is designed to address these challenges by providing a comprehensive analysis platform that enables restaurant analysts to assess customer behaviour, identify areas for improvement, and track the restaurant’s current ranking in the market. The app aims to deliver data-driven recommendations to help restaurants improve their services and grow their business.

The primary aim of RESTROLYZE is to empower restaurant analysts with tools to make informed decisions based on real-time data and customer feedback. The specific objectives of the app include:

1.	Customer Behaviour Analysis: 
	To analyse customer preferences, visit patterns, and feedback to identify trends.
2.	Improvement Identification: 
	To highlight areas of improvement in service, menu items, and overall customer experience.
3.	Ranking and Performance Tracking: 
	To assess the restaurant's ranking in comparison to competitors based on key performance indicators (KPIs).
4.	Data-Driven Decision Making: 
	To assist restaurant owners and analysts in making informed decisions that lead to the growth and improvement of their business

System Requirements

1. Client (Android Device):
•	Processor: Quad-core 1.8 GHz or higher
•	RAM: 4 GB or more
•	Storage: Minimum 100 MB of free space for app installation
•	Operating System: Android 8.0 (Oreo) or higher
•	Internet Connection: Stable internet connection for real-time data fetching and interaction with the server

2. Server:
•	Processor: Intel Core i5 or higher
•	RAM: 8 GB or more
•	Storage: Minimum 50 GB free disk space
•	Operating System: Linux (Ubuntu 18.04 or higher), Windows Server, or MacOS
•	Software Requirements:
•	Python 3.8 or higher
•	Flask 2.0 or higher (for API development)
•	Scikit-learn (for running machine learning models)
•	Gunicorn (for deploying the Flask app)
•	Database: SQLite or AWS
•	Internet Connection: High-speed internet for API hosting and handling multiple client requests
•	API Hosting Server: Heroku or any other API hosting service.

Steps to Execute this project : 
1. Install Android studio and include all the frontend files provided in the RestroLyze repository. Paste all the contents of the "Android Frontend" after creating a project in the Android Studio. path : " ..\RestroLyze\app\src\main"
2. After pulling the ML model, open the ML Model folder an open Jupyter Notebook into that file. After Opening the Jupyter Notebook, execute all the python notebook files and pickle the models. They will look something like "rating.pkl" and "Recommendation.pkl"
3. Now after pulling the API folder, paste the "ratings.pkl" and "Recommendation.pkl" files into that API folder. Now open the API files into the VS code editior, Pycharm or any other code editor which you like and run the API Files.
4. After executing the API files, your API will be active and ready to communicate with your frontend. Now just open the Android Studio and then just run the project of yours in an android device emulator given by android studio itself.

(Note : if you want export the apk of the android app and want to run the app on your android device, you can just host the API by using any API hosting sevice or if you have your own server, just change the API link from localhost to global host that your APP will be able to communicate remotely with the server.)

This APP is Open Source, it is free for everyone and if anyone want to work with me for making this APP more efficient, you can just mail me.
Mail : sarveshvarode545@gmail.com

I will look forward for your mail.
Happy Coding.
