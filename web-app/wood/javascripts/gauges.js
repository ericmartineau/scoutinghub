(function() {

  $(function() {
    var blue, grad, green, likes, orange, red, unique, views, visitors, visits, yellow;
    yellow = "#FFC125";
    orange = "#FF9609";
    green = "#80B031";
    blue = "#0096DB";
    red = "#f54f6c";
    grad = [green, yellow, orange];
    if ($("#visitors-gauge").length) {
      visitors = new JustGage({
        id: "visitors-gauge",
        value: 245,
        min: 0,
        max: 500,
        title: "Visitors",
        levelColors: grad,
        levelColorsGradient: true,
        gaugeWidthScale: 0.4
      });
    }
    if ($("#visits-gauge").length) {
      visits = new JustGage({
        id: "visits-gauge",
        value: 271,
        min: 0,
        max: 500,
        title: "New Visits",
        levelColors: grad,
        levelColorsGradient: true,
        gaugeWidthScale: 0.4
      });
    }
    if ($("#unique-gauge").length) {
      unique = new JustGage({
        id: "unique-gauge",
        value: 141,
        min: 0,
        max: 500,
        title: "Unique Visitors",
        levelColors: grad,
        levelColorsGradient: true,
        gaugeWidthScale: 0.4
      });
    }
    if ($("#views-gauge").length) {
      views = new JustGage({
        id: "views-gauge",
        value: 687,
        min: 0,
        max: 1000,
        title: "Page Views",
        levelColors: grad,
        levelColorsGradient: true,
        gaugeWidthScale: 0.4
      });
    }
    if ($("#likes-gauge").length) {
      return likes = new JustGage({
        id: "likes-gauge",
        value: 87,
        min: 0,
        max: 500,
        title: "Appreciations",
        levelColors: grad,
        levelColorsGradient: true,
        gaugeWidthScale: 0.4
      });
    }
  });

}).call(this);
