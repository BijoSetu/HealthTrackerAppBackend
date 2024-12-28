<template id="user-attributes-overview">
  <div>
    <app-layout>
      <h3>User Attributes</h3>

      <!-- Loading state -->
      <div v-if="loading" class="d-flex justify-content-center">
        <dotlottie-player
            src="https://lottie.host/87024446-999a-4516-ab14-42e439110b20/uyuoHWJO8A.lottie"
            background="transparent"
            speed="1"
            style="width: 100px; height: 100px"
            loop
            autoplay
        ></dotlottie-player>
      </div>

      <div v-if="userAttributes" class="row">
        <!-- Display user attributes -->
        <div class="col-md-4">
          <div class="card mb-4">
            <div class="card-header bg-secondary text-white">
              <div class="row">
                <div class="col-6"> User Profile </div>
                <div class="col" align="right">
                  <button rel="tooltip" title="Update"
                          class="btn btn-info btn-simple btn-link"
                          @click="updateUser()">
                    <i class="far fa-save" aria-hidden="true"></i>
                  </button>
                </div>
              </div>
            </div>
            <div class="card-body">
              <form>
                <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text" id="input-user-name">Age</span>
                  </div>
                  <input type="text" class="form-control" v-model="userAttributes.age" name="name" placeholder="Name"/>
                </div>
                <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text" id="input-user-email">Gender</span>
                  </div>
                  <input type="email" class="form-control" v-model="userAttributes.gender" name="email" placeholder="Email"/>
                </div>
                <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text" id="input-user-email">Weight</span>
                  </div>
                  <input type="email" class="form-control" v-model="userAttributes.weight" name="email" placeholder="Email"/>
                </div>
                <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text" id="input-user-email">Height</span>
                  </div>
                  <input type="email" class="form-control" v-model="userAttributes.height" name="email" placeholder="Email"/>
                </div>

              </form>
            </div>
          </div>
        </div>
      </div>

    </app-layout>
  </div>
</template>

<script>
app.component("user-attributes-overview", {
  template: "#user-attributes-overview",
  data: () => ({
    userAttributes: null,
    loading: true,
    age:null,
    gender:null,
    height:null,
    weight:null
  }),
  created() {
    // Set loading to true before making the API call
    this.loading = true;

    // Get the userId from local storage
    const userId = localStorage.getItem('userId');
    console.log('Retrieved User ID:', userId);

    // Fetch user attributes from the API
    axios.get(`/api/users/${userId}/attributes`)
        .then(res => {
          this.userAttributes = res.data.userAttributes;
          // set loadig to false after the api call is complete
          this.loading = false;
        })
        .catch(() => {
          alert("Error while fetching user attributes");
          // set loading to false after the api get into error
          this.loading = false;
        });
  },
  methods: {
    // update the users name or email
    updateUser: function () {
      const userId = localStorage.getItem('userId');
      const url = `/api/users/${userId}/attributes`
      axios.patch(url,
          {
            age:this.userAttributes.age,
            gender: this.userAttributes.gender,
            weight:this.userAttributes.weight,
            height:this.userAttributes.height
          }
          )
          .then(response =>
            this.user.push(response.data))
          .catch(error => {
            console.log(error)
          })
      alert("User updated!")
    }
  }
});
</script>
