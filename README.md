# UNESCO World Heritage 3D Globe Viewer

This Java application visualizes UNESCO World Heritage Sites on an interactive, 3D globe. It ingests a dataset of UNESCO World Heritage sites, displays them as clickable markers, and allows users to explore the world visually. The dataset was collected from an open-source Kaggle Dataset (link at the end) and then manually edited to remove unnecessary fields.
At its core, this program uses a HashMap with separate chaining to efficiently store and query site data by coordinates, enabling interactions with hundreds of sites.

## Data Ingestion

We process data from a CSV file (e.g., `unescosites.csv`) containing details of UNESCO World Heritage Sites. The `Injest.java` class handles this by using a `Scanner` to read the CSV file line by line, skipping the header. Each line is split into fields using a comma delimiter. For each site, a `Site` object is created to store its details, and a `Coord` object is created to hold the latitude and longitude. The data is stored in a `HashMap<Coord, Site>` within the `Injest` class, where the `Coord` object acts as the key and the `Site` object is the value.

## 2D Implementation (Beta Testing)

Before attempting the 3D sphere, we conducted beta testing using a 2D map visualization implemented in the `WorldMap.java` class. To run the file, use the following commands:

1.  Compile the Java files:
    ```bash
    javac -d bin heritage/*.java
    ```
2.  Run the 2D WorldMap application:
    ```bash
    java -cp bin heritage.WorldMap
    ```

We loaded an equirectangular 2D world map image and displayed it as the background of a `JPanel`. The method `convertCtoP` in `WorldMap.java` was used to translate the latitude and longitude values into 2D pixel (X, Y) coordinates, which are then used to visually represent it on the map. `JButton`s are used for each site and are positioned at the site's calculated X, Y coordinates on the 2D map. An `ActionListener` is attached to each button, which opens a new `JFrame` upon clicking, showing the Site's information.

## HashMap with Separate Chaining

To manage the spatial data efficiently, we implemented a `HashMap` where each bucket handles collisions using separate chaining through linked lists. Each UNESCO site is associated with a coordinate key (latitude and longitude) and mapped to information (site name, description, country, region).

## 3D Implementation

Instead of using a 3D engine, we created a 3D globe using 2D graphics primitives from Java’s AWT and Swing libraries. The core idea is to simulate a 3D globe using a grid of latitude and longitude lines, where each square is mapped to a location on the Earth's surface.

`MouseListener` and `MouseMotionListener` allow users to click and drag the globe, simulating rotation by adjusting the position of each square. Clicking on a point indicating a site marker highlights the site and displays its information in the terminal window. To run the file, use the following commands:

1.  Compile the Java files (if not already done):
    ```bash
    javac -d bin heritage/*.java
    ```
2.  Run the 3D SphereRender application:
    ```bash
    java -cp bin heritage.SphereRender
    ```
    And then wait for the 3D globe to render.

## Improvements/Current Bugs

While the current version of the UNESCO 3D Globe Viewer successfully renders and maps global heritage sites using AWT and Swing, there are areas where the program fell short.

*   **Clustered Sites:** Many UNESCO sites are clustered together, making it difficult to distinguish or interact with individual points on the globe. Implementing a zooming function would allow users to zoom in on a particular region (scroll wheel or zoom buttons). This would likely involve scaling and redrawing each square used in projection calculations and redrawing sites.
*   **Rotation Lag:** Rendering the globe while dragging to rotate causes noticeable lag, especially on lower-end machines. This is due to re-rendering every square and site marker on each drag event.
*   **Mirrored Globe:** The globe appears mirrored horizontally. Fixing this issue would most likely involve fixing the way we map the globe.

## Data Source Link

[UNESCO World Heritage Sites 2021 - Kaggle](https://www.kaggle.com/datasets/ramjasmaurya/unesco-heritage-sites2021)