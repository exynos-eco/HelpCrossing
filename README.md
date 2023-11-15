# HelpCrossing
This repository is an Example User Project for ENN SDK.
For more information, visit [Exynos Eco-system webpage](https://soc-developer.semiconductor.samsung.com/).

## Explanation
### Implementation
#### Model Conversion
This application utilizes a pre-converted NNC model from ML Commons [Github](https://github.com/mlcommons/mobile_models/blob/main/v2_0/Samsung/od.nnc).
Although training models specifically for the use case might yield preferable results, we used an open-source model to test our concept.

#### Detection and Output Processing
Upon conversion, import the model to the HelpCrossing application.
HelpCrossing processes camera feed to detect vehicles.
This process encompasses:
- Setting image analyzer ([code](https://github.com/exynos-eco/HelpCrossing/blob/main/app/src/main/java/com/samsung/helpcrossing/fragments/CameraFragment.kt#L106))
- Executing model using ENN Framework ([code](https://github.com/exynos-eco/HelpCrossing/blob/main/app/src/main/java/com/samsung/helpcrossing/executor/ModelExecutor.kt#L65))
- Processing the output of the model to determine detected vehicles ([code](https://github.com/exynos-eco/HelpCrossing/blob/main/app/src/main/java/com/samsung/helpcrossing/executor/ModelExecutor.kt#L114))

#### Detection Feedback
When invoked, HelpCrossing provides detection feedback.
The means of feedback are:
- Text-To-Speech (TTS) Technology ([code](https://github.com/exynos-eco/HelpCrossing/blob/main/app/src/main/java/com/samsung/helpcrossing/executor/CrossingEvent.kt#L32))
- Toast Message ([code](https://github.com/exynos-eco/HelpCrossing/blob/main/app/src/main/java/com/samsung/helpcrossing/executor/CrossingEvent.kt#L31))


### Usage
#### How to Use
To run the application:
1. Download the project from Github [repository](https://github.com/exynos-eco/HelpCrossing/tree/main)
1. Open the project on Android Studio
1. Install the application on the 9925 ERD Board
1. Upon launching the application, grant camera permission
1. Change settings if necessary
1. When touching the screen, feedback is provided according to the detection status

#### Use Cases
As a proof-of-concept, HelpCrossing may serve the following use cases:
- **Urban Safety:** Assisting pedestrians in bustling urban areas with real-time safety assessments.
- **Traffic Management:** Providing data regarding vehicle flow for traffic management purposes.
- **Accessibility:** Supporting individuals with visual impairments navigating road crossings.
