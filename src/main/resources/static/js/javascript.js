
    var keyCount = 0;
    var isCounting = false;

    function startCounting() {
	alert("hello");
      isCounting = true;
      document.addEventListener("keydown", countKey);
    }

    function stopCounting() {
      isCounting = false;
      document.removeEventListener("keydown", countKey);
      document.getElementById("keyCount").value = keyCount;
    }

    function countKey() {
      if (isCounting) {
        keyCount++;
      }
    }
