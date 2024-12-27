<template id="lifestyle-suggestion">
  <div>
    <app-layout>

      <h3>Lifestyle Suggestion</h3>
      <!--      if the loading is true it will show a lottie loading animation, for ux-->

      <div v-if="loading" class="d-flex justify-content-center">
        <dotlottie-player
            src="https://lottie.host/87024446-999a-4516-ab14-42e439110b20/uyuoHWJO8A.lottie"
            background="transparent"
            speed="1"
            style="width: 300px; height: 300px"
            loop
            autoplay
        ></dotlottie-player>
      </div>
      <div class="row">
        <div class="col-md-4" v-for="lifestyleSuggestion in lifestyleSuggestions">
          <div class="card mb-4">

            <div class="card-header bg-secondary text-white">
              <h5 class="card-title">Here are your suggestions from past week</h5>
            </div>
            <div class="card-body">
              <ul>
                <li>
                  {{lifestyleSuggestion}}
                </li>
              </ul>

            </div>
          </div>
      </div>
      </div>
    </app-layout>

  </div>
</template>

<script>
app.component("lifestyle-suggestion",{
  template: "#lifestyle-suggestion",
  data: () => ({
    lifestyleSuggestions: [],
    loading:true
  }),
  created() {
    // getting userid from local storage
    const userId = localStorage.getItem('userId');
    // logging user id for debugging
    console.log('Retrieved User ID:', userId);
    axios.get(`/api/users/${userId}/life-style-suggestion`)
        .then(res => {
          this.lifestyleSuggestions = res.data;
          this.loading=false
        })
        .catch(() => alert("Error while fetching activities"));
  }
});
</script>
