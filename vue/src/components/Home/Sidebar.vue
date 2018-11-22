<template>
    <div id=sidebar>
        <div class="title flex valign halign">
            miniTerm
        </div>

    </div>
</template>

<script>
export default {};
</script>

<style scoped lang=scss>
#sidebar {
  flex-shrink: 0;
  width: 300px;
  border-right: 1px solid rgba(0, 0, 0, 0.1);
  .title {
    padding: 1rem;
    font-size: 20px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
}
</style>
