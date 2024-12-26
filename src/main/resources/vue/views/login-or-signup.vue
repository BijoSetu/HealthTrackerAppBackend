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
                  :style="{ backgroundColor: '#81C784', color: 'white' }">
                Switch to Sign Up
              </button>
            </div>

            <!-- Login Form -->
            <form>
              <div class="form-group mb-3">
                <label for="login-email">Email</label>
                <input
                    type="email"
                    id="login-email"
                    class="form-control"
                    placeholder="Enter your email"
                    required />
              </div>
              <div class="form-group mb-3">
                <label for="login-password">Password</label>
                <input
                    type="password"
                    id="login-password"
                    class="form-control"
                    placeholder="Enter your password"
                    required />
              </div>
              <button type="submit" class="btn btn-block" :style="{ backgroundColor: '#81C784', color: 'white' }">
                Login
              </button>
            </form>

            <!-- Sign Up Form (hidden by default) -->
            <form v-show="false">
              <div class="form-group mb-3">
                <label for="signup-name">Name</label>
                <input
                    type="text"
                    id="signup-name"
                    class="form-control"
                    placeholder="Enter your name"
                    required />
              </div>
              <div class="form-group mb-3">
                <label for="signup-email">Email</label>
                <input
                    type="email"
                    id="signup-email"
                    class="form-control"
                    placeholder="Enter your email"
                    required />
              </div>
              <div class="form-group mb-3">
                <label for="signup-password">Password</label>
                <input
                    type="password"
                    id="signup-password"
                    class="form-control"
                    placeholder="Enter your password"
                    required />
              </div>
              <button type="submit" class="btn btn-block" :style="{ backgroundColor: '#81C784', color: 'white' }">
                Sign Up
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
app.component('login-or-signup',{template: "#login-or-signup"});
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
  color: #81C784;
}
</style>
