import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Recorder {
    private final AtomicReference<Map<Driver, Set<Lap>>> result = new AtomicReference<>(new HashMap<>());
    private final AtomicReference<Map<Driver, Lap>> bestLap = new AtomicReference<>(new HashMap<>());
    private final AtomicReference<Map<Driver, Lap>> fastestLap = new AtomicReference<>(new HashMap<>());
    private final AtomicReference<Map<Driver, Lap>> fastestSector1= new AtomicReference<>(new HashMap<>());
    private final AtomicReference<Map<Driver, Lap>> fastestSector2 = new AtomicReference<>(new HashMap<>());
    private final AtomicReference<Map<Driver, Lap>> fastestSector3 = new AtomicReference<>(new HashMap<>());

    public void addLap(Driver driver, Lap lap) {
        result.updateAndGet(map -> {
            Set<Lap> driverLaps =
                    Optional.ofNullable(map.get(driver))
                            .orElse(new LinkedHashSet<>());

            driverLaps.add(lap);
            map.put(driver, driverLaps);

            return map;
        });

        if(lap.getSectors() != null){
            fastestSector1.updateAndGet(map -> {
                Sector bestSector1 =
                        map.entrySet()
                                .stream()
                                .findFirst()
                                .flatMap(entry -> entry.getValue()
                                        .getSectors()
                                        .stream()
                                        .filter(sector -> sector.getId() == 0)
                                        .findFirst())
                                .orElse(null);

                Sector currentSector1 =
                        lap.getSectors().stream()
                                .filter(sector -> sector.getId() == 0)
                                .findFirst()
                                .orElse(null);

                if((bestSector1 == null && currentSector1.getSectorTime() != null)
                        || currentSector1.getSectorTime() != null && currentSector1.getSectorTime().getTime() < bestSector1.getSectorTime().getTime()) {
                    map = new HashMap<>();
                    map.put(driver, lap);

//                    System.out.println("Fastest Sector 1: "+driver.getId()+" - "+lap.getId()+" - "+currentSector1.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(currentSector1.getSectorTime()));
                }

                return map;
            });
        }

        if(lap.getSectors() != null){
            fastestSector2.updateAndGet(map -> {
                Sector bestSector2 =
                        map.entrySet()
                                .stream()
                                .findFirst()
                                .flatMap(entry -> entry.getValue()
                                        .getSectors()
                                        .stream()
                                        .filter(sector -> sector.getId() == 1)
                                        .findFirst())
                                .orElse(null);

                Sector currentSector2 =
                        lap.getSectors().stream()
                                .filter(sector -> sector.getId() == 1)
                                .findFirst()
                                .orElse(null);

                if((bestSector2 == null && currentSector2 != null && currentSector2.getSectorTime() != null)
                        || currentSector2 != null && currentSector2.getSectorTime() != null && currentSector2.getSectorTime().getTime() < bestSector2.getSectorTime().getTime()) {
                    map = new HashMap<>();
                    map.put(driver, lap);

//                    System.out.println("Fastest Sector 2: "+driver.getId()+" - "+lap.getId()+" - "+currentSector2.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(currentSector2.getSectorTime()));
                }

                return map;
            });
        }

        if(lap.getSectors() != null){
            fastestSector3.updateAndGet(map -> {
                Sector bestSector3 =
                        map.entrySet()
                                .stream()
                                .findFirst()
                                .flatMap(entry -> entry.getValue()
                                        .getSectors()
                                        .stream()
                                        .filter(sector -> sector.getId() == 2)
                                        .findFirst())
                                .orElse(null);

                Sector currentSector3 =
                        lap.getSectors().stream()
                                .filter(sector -> sector.getId() == 2)
                                .findFirst()
                                .orElse(null);

                if((bestSector3 == null && currentSector3 != null && currentSector3.getSectorTime() != null)
                        || currentSector3 != null && currentSector3.getSectorTime() != null && currentSector3.getSectorTime().getTime() < bestSector3.getSectorTime().getTime()) {
                    map = new HashMap<>();
                    map.put(driver, lap);

//                    System.out.println("Fastest Sector 3: "+driver.getId()+" - "+lap.getId()+" - "+currentSector3.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(currentSector3.getSectorTime()));
                }

                return map;
            });
        }

        if(lap.getLapTime() != null){
            bestLap.updateAndGet(map -> {
                Lap driverBestLap = map.get(driver);
                if(driverBestLap == null
                        || lap.getLapTime().getTime() < driverBestLap.getLapTime().getTime()) {
                    map.put(driver, lap);
                    System.out.println("BEST LAPS: " +
                            map.entrySet()
                                    .stream()
                                    .sorted((e1, e2) -> e1.getValue().getLapTime().compareTo(e2.getValue().getLapTime()))
                                    .collect(Collectors.toMap(key -> key.getKey().getId(), value -> new SimpleDateFormat("mm:ss.SSS").format(value.getValue().getLapTime()),
                                            (e1, e2) -> e1, LinkedHashMap::new)));
                }

                return map;
            });

            fastestLap.updateAndGet(map -> {
                Map.Entry<Driver, Lap> bestLap =
                        map.entrySet()
                                .stream()
                                .findFirst()
                                .orElse(null);

                if(bestLap == null
                        || lap.getLapTime().getTime() < bestLap.getValue().getLapTime().getTime()) {
                    map = new HashMap<>();
                    map.put(driver, lap);

//                    System.out.println("Fastest lap: "+driver.getId()+" - "+lap.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(lap.getLapTime()));
                }

                return map;
            });
        }
    }

    public Map<Driver, Set<Lap>> getResult() {
        return result.get();
    }

    public Map<Driver, Lap> getBestLaps() {
        return bestLap.get();
    }

    public Map<Driver, Lap> getOverallBestLaps() {
        return fastestLap.get();
    }

    public Map<Driver, Lap> getBestSector1() {
        return fastestSector1.get();
    }

    public Map<Driver, Lap> getBestSector2() {
        return fastestSector2.get();
    }

    public Map<Driver, Lap> getBestSector3() {
        return fastestSector3.get();
    }

}
