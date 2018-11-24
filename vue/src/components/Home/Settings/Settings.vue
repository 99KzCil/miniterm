<template>
    <div id="settings" class="flex flex1 column pad">
        <div class="content flex column flex1" v-if="page=='settings'">
            <div class="settingsButton flex valign pad pointer noselect" @click="page='connections'">
                <i class="material-icons">desktop_windows</i>
                <span class="flex1 talign">connections</span>
            </div>
        </div>
        <connections v-if="page=='connections'"/>

    </div>
</template>

<script>
import Connections from "./Connections";
export default {
  components: {
    Connections
  },
  data() {
    return {
      page: "settings"
    };
  }
};
</script>

<style scoped lang=scss>
#settings {
  .settingsButton {
    transition: background .25s;
    &:hover {
      background: lightgray;
    }
  }
}
</style>
