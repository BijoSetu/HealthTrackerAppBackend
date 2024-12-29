<template id="user-profile">
  <app-layout>
    <!--      if the loading is true it will show a lottie loading animation, for ux-->

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

    <div class="card bg-light mb-3">
      <div class="card-header">
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
              <span class="input-group-text" id="input-user-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="user.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-email">Email</span>
            </div>
            <input type="email" class="form-control" v-model="user.email" name="email" placeholder="Email"/>
          </div>

        </form>

      </div>
    </div>
  </app-layout>
</template>


<script>
app.component("user-profile", {
  template: "#user-profile",
  data: () => ({
    user: null,
    loading:true
  }),
  created: function () {
    // get the user id from local storage
    const userId = localStorage.getItem('userId');
    console.log('Retrieved User ID:', userId);
    const url = `/api/users/id/${userId}`
    axios.get(url)
        .then(res => {
          this.user = res.data;
          this.loading=false
        })
        .catch(() => alert("Error while fetching user" + userId));
    console.log(this.user)
  },
  methods: {
    // update the users name or email
    updateUser: function () {
      const userId = localStorage.getItem('userId');
      const url = `/api/users/${userId}`
      axios.patch(url,
          {
         name:this.user.name,
            email: this.user.email,

          })
          .then(response => {
                this.user.push(response.data);
                localStorage.setItem("userName",response.data.user.name)
              }
          )
          .catch(error => {
            console.log(error)
          })
      alert("User updated!")
    }
  }

});
</script>


