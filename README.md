# JeevAyush (Smart India Hackathon 2022 Finalist)

Android application to locate nearby AYUSH hospitals (Hospitals offering services like Ayurveda, Yoga and Naturopathy, Unani, Siddha and Homeopathy) based on users location.

Features:

* Services provider seeking to register themselves on the app can do so by filling the registeration form.
* Admin panel of the app will approve the hospitals.
* Registered hopsitals (ones approved by admin) are displayed on the map based on users location.
* User can filter the search by searching for the service provider based on distance or by the symptoms he/she is feeling (this is taken care by a trained classifier ML model).
* On clicking the service provider, details related to them are displayed.User can see the details of all services provider near his/her location.
* User can click on the hopsital/service provider to book an appointment with, in which case the particular hospital is notified about the user.


## User Journey 1: Admin Interface ##

Functionalities:
* Admin can view list of all hospitals that want to register themselves. 
* Can accept / reject request based on the information and documents submitted by these service provider.

<p align="center">
  <img src="/home1.jpg" width="190" />
  <img src="/admin login.jpg" width="190" /> 
   <img src="/Admin.jpg" width="190" />
  <img src="/Admin_ Hospital 1 view.jpg" width="190" /> 
  
</p>

## User Journey 2: Consumer Panel ##
Functionalities:
* After fetching the current location of the consumer nearby service providers are displayed on the app ,more information is displayed when a particular hospital is clicked. 
* Can view list of hospitals and a important information pertaining to them. 
* On clicking the hospitals user can find a page to book an appointment.

<p align="center">
 
  <img src="/user filter.jpg" width="190" />
  <img src="/display_map.png" width="190" />
  <img src="/hospital_list.png" width="190" />
  <img src="/Single hospital from list.jpg" width="190" />
  
</p>

## User Journey 3: Service Provider Interface ##
Functionalities:
* Service providers can fill the registeration form to get themselves registered. 
* They can view list of patients who intend to book an appointment with them. 
* On clicking particular user all information is displayed.

<p align="center">
<img src="/Hospital 1.jpg" width="190" />
  <img src="/hospital_registration.png" width="190" />
  <!---<img src="/Post registration msg.jpg" width="190" />-->
  <img src="/Hospital after sign in.jpg" width="190" />
  <img src="/user_info.png" width="190" />
  
</p>

## Technology Stack ##
<p>Frontend: Android Studio, Java, XML </p>

<p>Backend:  NodeJs , ExpressJs </p>

<p>Database: Firebase, MongoDB </p>
<p>ML: SciKit Learn </p>
<p>Cloud: Amazon Web Service </p>
