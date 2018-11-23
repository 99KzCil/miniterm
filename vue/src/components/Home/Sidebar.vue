<template>
  <div id=sidebar>
    <div class="title flex valign halign">
      miniTerm
    </div>
    <div @click="localhost" class="pad pointer">
      Localhost
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    localhost() {
      var connection = {
        id: generateUUID(),
        name: "localhost",
        hostname: "localhost",
        port: 22,
        type: "ssh"
      };
      this.$parent.newConnection(connection);
    }
  }
};
function generateUUID() {
  // Public Domain/MIT
  var d = new Date().getTime();
  if (
    typeof performance !== "undefined" &&
    typeof performance.now === "function"
  ) {
    d += performance.now(); //use high-precision timer if available
  }
  return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function(c) {
    var r = (d + Math.random() * 16) % 16 | 0;
    d = Math.floor(d / 16);
    return (c === "x" ? r : (r & 0x3) | 0x8).toString(16);
  });
}
</script>


<style scoped lang=scss>
#sidebar {
  flex-shrink: 0;
  width: 300px;
  border-right: 1px solid rgba(0, 0, 0, 0.1);
  .title {
    height: 40px;
    font-size: 20px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
}
</style>
