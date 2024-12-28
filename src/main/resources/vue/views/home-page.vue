<!-- the "home-page" element is passed as a parameter to VueComponent in the JavalinConfig file -->
<template id="home-page">
  <app-layout>
     <div class="main-container" style="background-color: #F5F7FA; height: 50vh; width: 100vw; display: flex; flex-direction: column; padding: 20px;">
      <!-- Welcome Section -->
       <div class="welcome-section" style="display: flex; align-items: center; justify-content: flex-start; margin-bottom: 20px;">
         <h1 class="welcome-heading" style="font-size: 2rem; color: #3C4858; margin: 0;">
           <i class="fas fa-user-circle" style="margin-right: 10px;"></i>
           Welcome, {{ userName }}!
         </h1>

         <a
             href="/user-attributes"
             class="btn btn-primary"
             style="margin-left: 20px; font-size: 1rem; padding: 10px 20px;">
           Show Your Biometrics
         </a>

<!--feature to be added later -->
<!--         <a-->
<!--             href="/user-attributes"-->
<!--             class="btn btn-primary"-->
<!--             style="margin-left: 20px; font-size: 1rem; padding: 10px 20px;">-->
<!--          Add a Daily Goal-->
<!--         </a>-->
       </div>


       <!-- First container section -->
      <div class="split-section" style="display: flex; flex: 1 1 auto;">
        <!-- Daily Goals -->
        <dotlottie-player src="https://lottie.host/aa1fd7bb-a610-46a7-b61c-410a7b597635/rpSgatxs1L.lottie" background="transparent" speed="1" style="width: 100px; height: 200px" loop autoplay></dotlottie-player>
        <div class="split-box" style="flex: 1; margin-right: 10px; background: #FFF; border-radius: 8px; padding: 20px; display: flex; flex-direction: column; justify-content: space-between; align-items: center;">
          <h2 class="section-title" style="font-size: 1.5rem; color: #3C4858;">
            <i class="fas fa-check-circle" style="margin-right: 10px;"></i>
            Daily Goals</h2>
          <h2>You have added a total of {{ dailygoals.length }} dailygoals</h2>
          <button class="btn btn-primary" style="background-color: #81C784; border-color: #81C784;" onclick="window.location.href='/daily-goals-overview'">Show Dailygoals</button>
        </div>

        <!-- Milestones -->
        <div class="split-box" style="flex: 1; margin-left: 10px; background: #FFF; border-radius: 8px; padding: 20px; display: flex; flex-direction: column; justify-content: space-between; align-items: center;">
          <h2 class="section-title" style="font-size: 1.5rem; color: #3C4858;">
            <i class="fas fa-trophy" style="margin-right: 10px;"></i>
            Milestones</h2>
          <h2>You have accomplished {{milestones.length }} milestones</h2>
          <button class="btn btn-primary" style="background-color: #81C784; border-color: #81C784;" onclick="window.location.href='/milestones-overview'">View Milestones</button>
        </div>
      </div>
    </div>


    <!-- Bottom Section: split into BMI and daily habits -->
    <div class="main-container" style="background-color: #F5F7FA; height: 50vh; width: 100vw; display: flex; flex-direction: row; padding: 20px;">
      <!-- Left Side: BMI Calculator -->
      <div class="bmi-calculator" style="flex: 1; margin-right: 10px; background-color: #FFF; padding: 20px; border-radius: 8px; display: flex; flex-direction: column; align-items: center;">
        <h2 style="font-size: 2rem; color: #3C4858; margin-bottom: 20px;">
          <i class="fas fa-weight" style="margin-right: 10px;"></i>
          BMI Calculator</h2>

        <!-- Input Fields for taking the height and weight from the user , using the v-model -->
        <div style="display: flex; flex-direction: column; align-items: center;">
          <label for="weight" style="font-size: 1rem; color: #3C4858; margin-bottom: 10px;">Enter your weight (kg):</label>
          <input v-model="weight" id="weight" type="number" placeholder="Weight in kg" style="padding: 10px; margin-bottom: 10px; border-radius: 8px; border: 1px solid #ccc; width: 200px;"/>

          <label for="height" style="font-size: 1rem; color: #3C4858; margin-bottom: 10px;">Enter your height (cm):</label>
          <input v-model="height" id="height" type="number" placeholder="Height in cm" style="padding: 10px; margin-bottom: 10px; border-radius: 8px; border: 1px solid #ccc; width: 200px;"/>

          <!-- Calculate BMI Button -->
          <button @click="calculateBMI()" style="background-color: #81C784; border-color: #81C784; padding: 10px 20px; border-radius: 8px; color: white; font-size: 1rem;">Calculate BMI</button>
        </div>

        <!-- Displaying the bmi result -->
        <div v-if="bmi !== null" id="bmiResult" style="margin-top: 20px; font-size: 1.5rem; color: #3C4858; font-weight: bold;">
          <h2 style="font-size: 2rem; color: #3C4858; margin-bottom: 20px;">
            Your BMI is {{bmi}}
          </h2>
        </div>
      </div>

      <!-- Right Side: Daily Habits -->
      <div class="daily-habits" style="flex: 1; margin-left: 10px; background: #FFF; border-radius: 8px; padding: 20px; display: flex; flex-direction: column; justify-content: space-between; align-items: center;">
        <h2 class="section-title" style="font-size: 1.5rem; color: #3C4858;">
          <i class="fas fa-sync" style="margin-right: 10px;"></i>
          Daily Habits
        </h2>
        <button class="btn btn-primary" style="background-color: #81C784; border-color: #81C784; margin-bottom: 10px;" onclick="window.location.href='/daily-habits-overview'">Check DailyHabits</button>
        <button class="btn btn-primary" style="background-color: #81C784; border-color: #81C784; display: flex; align-items: center;" onclick="window.location.href='/lifestyle-suggestion'">
          <i class="fas fa-smile" style="margin-right: 8px; font-size: 1.2rem;"></i> Get Lifestyle Suggestion
        </button>
      </div>
    </div>

    <!-- Adding some space before the footer to avoid overlap -->
    <div style="height: 150px;"></div>

  </app-layout>

</template>


<script>
app.component('home-page',
    {
      template: "#home-page",
      data: () => ({
        milestones: [],
        users: [],
        dailygoals: [],
        bmi:null,
        weight:null,
        height:null,
        userName:null
      }),
      created() {
        // getting the username , to show at the welcome heading
        this.userName = localStorage.getItem('userName');
        // getting userid from the local storage
        const userId = localStorage.getItem('userId');
        // logging the user id for debugging
        console.log('Retrieved User ID:', userId);
        axios.get(`/api/users/${userId}/milestones`)
            .then(res => {
              console.log("Milestones Response:", res); // Log the full response
              this.milestones = res.data.milestones; // Assign the data to the milestones variable
            })
            .catch(() => {
              alert("Error while fetching milestones");
            });


        axios.get(`/api/users/id/${userId}`)
            .then(res => this.users = res.data.user)
            .catch(() => alert("Error while fetching users"));
        axios.get(`/api/users/${userId}/daily-goals`)
            .then(res => {
              console.log("Dailygoals Response:", res);
              this.dailygoals = res.data
            })
            .catch(() => alert("Error while fetching activities"));
      },
      methods: {
        calculateBMI() {
          // Ensure weight and height are provided
          if (this.weight !== null  && this.height !== null) {
            // converting height to meters
            const heightInMeters = this.height / 100;
            // Calculate the bmi
            const bmi = this.weight / (heightInMeters * heightInMeters);
            // Set the calculated BMI value
            this.bmi = bmi.toFixed(2);
          } else {
            //  alert the user if fields are empty
            alert("Please enter both weight and height.");
          }
        }
      }
    });

</script>

