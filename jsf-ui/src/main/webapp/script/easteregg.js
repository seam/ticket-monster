var clkCnt = 0;

function regClk() {
  clkCnt++;
  if (clkCnt == 5) {
    clkCnt = 0;
    rawr(); 
  }
}

function rawr() {
  document.getElementById("rawr").style.display = "block";
  window.setTimeout(hideRawr, 2000);
}

function hideRawr() {
  document.getElementById("rawr").style.display = "none";
}