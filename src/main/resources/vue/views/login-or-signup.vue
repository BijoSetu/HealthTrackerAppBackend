<template id="login-or-signup">
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <h1 class="text-left health-now">Health Now</h1>
        <div class="card shadow-lg">

          <div class="card-body">

            <h3 class="text-center fw-bold" :style="{ color: '#81C784' }">Welcome!</h3>

            <!-- Toggle Login/Signup -->
            <div class="text-center mb-4">
              <button
                  class="btn btn-sm btn-outline-success"
                  :style="{ backgroundColor: '#81C784', color: 'white' }"
                  @click="toggleForm"
              >
<!--                toggle between login or signup screen when the button is pressed-->
                Switch to {{ isLogin ? 'Sign Up' : 'Login' }}
              </button>
            </div>

            <!-- Login Form -->
            <form v-show="isLogin">
              <div class="form-group mb-3">
                <label for="login-email">Email</label>
                <input
                    type="email"
                    id="login-email"
                    v-model="loginEmail"
                    class="form-control"
                    placeholder="Enter your email"
                    required
                />
              </div>
              <div class="form-group mb-3">
                <label for="login-password">Password</label>
                <input
                    type="password"
                    id="login-password"
                    v-model="loginPassword"
                    class="form-control"
                    placeholder="Enter your password"
                    required
                />
              </div>
              <button type="submit" class="btn btn-block" :style="{ backgroundColor: '#81C784', color: 'white' }" @click.prevent="login">
                Login
              </button>
            </form>

            <!-- Sign Up Form (hidden by default) -->
            <form v-show="!isLogin">
              <div class="form-group mb-3">
                <label for="signup-name">Name</label>
                <input
                    type="text"
                    id="signup-name"
                    v-model="signupName"
                    class="form-control"
                    placeholder="Enter your name"
                    required
                />
              </div>
              <div class="form-group mb-3">
                <label for="signup-email">Email</label>
                <input
                    type="email"
                    id="signup-email"
                    v-model="signupEmail"
                    class="form-control"
                    placeholder="Enter your email"
                    required
                />
              </div>
              <div class="form-group mb-3">
                <label for="signup-password">Password</label>
                <input
                    type="password"
                    id="signup-password"
                    v-model="signupPassword"
                    class="form-control"
                    placeholder="Enter your password"
                    required
                />
              </div>
              <button type="submit" class="btn btn-block" :style="{ backgroundColor: '#81C784', color: 'white' }" @click.prevent="signup">
                Sign Up
              </button>
              <div v-if="signupError" class="text-danger mt-2">
                {{ signupError }}
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
app.component('login-or-signup', {
  template: '#login-or-signup',
  data: () => ({
    // by default login screen is shown at first
    isLogin: true,
    // data needed for login
    loginEmail: '',
    loginPassword: '',
    // data needed for signing up the user
    signupName: '',
    signupEmail: '',
    signupPassword: '',
    // storing the error message
    signupError: '',
  }),

  methods: {
    // toggle between login and signup pages
    toggleForm() {
      this.isLogin = !this.isLogin;
      // resetting errors
      this.signupError = '';
    },

    // function for logging in the user
    login() {
      const url = '/api/users/login';
      axios
          .post(url, {
            email: this.loginEmail,
            password: this.loginPassword,
          })
          .then((res) => {
            if (res.data.success && res.data.user) {
              const userId = res.data.user.userid;
              const userName = res.data.user.name;
              console.log('Retrieved User ID:', userId);

              console.log('Login successful', res.data);
              // store username in local storage to be used on ui. eg welcome message
              localStorage.setItem('userName', userName);
              // store the user id to be used during api calls
              localStorage.setItem('userId', userId);
              // when login is successful redirect to the homepage
              window.location.href = '/homepage';
            } else {
              alert(res.data.error || 'Login failed! Please check your credentials.');
            }
          })
          .catch((error) => {
            console.error('Login failed', error);
            alert(error.response?.data?.error || 'An unexpected error occurred during login.');
          });
    }

    ,

    //  signing in the user
    signup() {
      const url = '/api/users/signup';
      axios
          .post(url, {
            name: this.signupName,
            email: this.signupEmail,
            password: this.signupPassword,
          })
          .then((response) => {
            if (response.status === 201) {
              console.log('Signup successful', response.data);
              alert('Signup successful! Please log in.');
              // after signing up user has to go to login page again and login using the created creds
              window.location.href = '/';
            }
          })
          .catch((error) => {
            if (error.response && error.response.status === 400) {
              alert('User already exists. Please use a different email.');
            } else {
              alert('An error occurred during signup. Please try again.');
            }
          });
    }

  },
});

</script>

<style scoped>
.card {
  border-radius: 15px;
}

.btn-block {
  width: 100%;
}

.form-control {
  border-radius: 10px;
}

.health-now {
  font-family: 'Roboto', sans-serif;
  font-weight: 700;
  color: #81c784;
}
</style>
