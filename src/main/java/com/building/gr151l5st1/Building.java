/*
 * Copyright (c) 2018 Babiosik
 * Данная лицензия разрешает лицам, получившим копию данного программного обеспечения и сопутствующей документации (в дальнейшем именуемыми «Программное Обеспечение»), безвозмездно использовать Программное Обеспечение без ограничений, включая неограниченное право на использование, копирование, изменение, слияние, публикацию, распространение, сублицензирование и/или продажу копий Программного Обеспечения, а также лицам, которым предоставляется данное Программное Обеспечение, при соблюдении следующих условий:
 * Указанное выше уведомление об авторском праве и данные условия должны быть включены во все копии или значимые части данного Программного Обеспечения.
 * ДАННОЕ ПРОГРАММНОЕ ОБЕСПЕЧЕНИЕ ПРЕДОСТАВЛЯЕТСЯ «КАК ЕСТЬ», БЕЗ КАКИХ-ЛИБО ГАРАНТИЙ, ЯВНО ВЫРАЖЕННЫХ ИЛИ ПОДРАЗУМЕВАЕМЫХ, ВКЛЮЧАЯ ГАРАНТИИ ТОВАРНОЙ ПРИГОДНОСТИ, СООТВЕТСТВИЯ ПО ЕГО КОНКРЕТНОМУ НАЗНАЧЕНИЮ И ОТСУТСТВИЯ НАРУШЕНИЙ, НО НЕ ОГРАНИЧИВАЯСЬ ИМИ. НИ В КАКОМ СЛУЧАЕ АВТОРЫ ИЛИ ПРАВООБЛАДАТЕЛИ НЕ НЕСУТ ОТВЕТСТВЕННОСТИ ПО КАКИМ-ЛИБО ИСКАМ, ЗА УЩЕРБ ИЛИ ПО ИНЫМ ТРЕБОВАНИЯМ, В ТОМ ЧИСЛЕ, ПРИ ДЕЙСТВИИ КОНТРАКТА, ДЕЛИКТЕ ИЛИ ИНОЙ СИТУАЦИИ, ВОЗНИКШИМ ИЗ-ЗА ИСПОЛЬЗОВАНИЯ ПРОГРАММНОГО ОБЕСПЕЧЕНИЯ ИЛИ ИНЫХ ДЕЙСТВИЙ С ПРОГРАММНЫМ ОБЕСПЕЧЕНИЕМ. 
 */
package com.building.grKN01151st1;

import eu.printingin3d.javascad.coords.Angles3d;
import java.util.ArrayList;
import java.util.List;

import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Sphere;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords.Dims3d;
import eu.printingin3d.javascad.coords.Triangle3d;
import eu.printingin3d.javascad.models.*;
import eu.printingin3d.javascad.tranzitions.Difference;
import eu.printingin3d.javascad.tranzitions.Scale;
import eu.printingin3d.javascad.tranzitions.Union;
import sun.rmi.server.UnicastRef;
/**
 *
 * @author Санта
 */
class Building extends Union {
    
    Building() {
        super(getModels());
    }
    private static Abstract3dModel Airplane () {
        return new Union(
            new Cylinder(0.8, 0.1, 0.6).rotate(Angles3d.yOnly(90))
                .move(Coords3d.xOnly(-5.4)),
            new Cylinder(4, 0.6, 1.6).rotate(Angles3d.yOnly(90))
                .move(Coords3d.xOnly(-3)),
            new Cylinder(1.5, 1.5).rotate(Angles3d.yOnly(90))
                .move(Coords3d.xOnly(-0.25)),
            new Difference(
                new Cylinder(4, 1.6, 0.5), 
                new Cylinder(2, 0.6).move(Coords3d.zOnly(1.7))
            ).rotate(Angles3d.yOnly(90)).move(Coords3d.xOnly(2.5)),
            new Difference(
                new Cube(new Dims3d(6, 11, 0.1)),
                new Cube(new Dims3d(10, 10, 0.2))
                    .move(new Coords3d(2, 7, 0))
                    .rotate(Angles3d.zOnly(35)),
                new Cube(new Dims3d(10, 10, 0.2))
                    .move(new Coords3d(2, -7, 0))
                    .rotate(Angles3d.zOnly(-35))
            )
        );
    }
    static Abstract3dModel newCube (double x, double y, double z) {
        return new Cube(new Dims3d(x, y, z));
    }
    static Abstract3dModel EmptyCube (double x, double y, double z) {
        return EmptyCube(new Dims3d(x, y, z), 0.2);
    }
    static Abstract3dModel EmptyCube (Dims3d dims) { return EmptyCube(dims, 0.5); }
    static Abstract3dModel EmptyCube (Dims3d dims, double widthWall) {
        return new Union(
            new Cube(new Dims3d(dims.getX(), widthWall, dims.getZ()))
                .move(Coords3d.yOnly( (dims.getY()-widthWall) / 2)),
            new Cube(new Dims3d(dims.getX(), widthWall, dims.getZ()))
                .move(Coords3d.yOnly(-(dims.getY()-widthWall) / 2)),
            new Cube(new Dims3d(widthWall, dims.getY(), dims.getZ()))
                .move(Coords3d.xOnly( (dims.getX()-widthWall) / 2)),
            new Cube(new Dims3d(widthWall, dims.getY(), dims.getZ()))
                .move(Coords3d.xOnly(-(dims.getX()-widthWall) / 2)),
            new Cube(new Dims3d(dims.getX(), dims.getY(), widthWall))
                .move(Coords3d.zOnly( (dims.getZ()-widthWall) / 2)),
            new Cube(new Dims3d(dims.getX(), dims.getY(), widthWall))
                .move(Coords3d.zOnly(-(dims.getZ()-widthWall) / 2))
        );
    }
    static Abstract3dModel FirstTower () {
        Abstract3dModel model = new Union (
            EmptyCube(10, 10, 10),
            EmptyCube(11, 11, 3).move(Coords3d.zOnly(6.5)),
            newCube(10, 0.4, 10).move(Coords3d.yOnly(-2)),
            newCube(10.5, 10.5, 0.5).move(Coords3d.zOnly(5)),
            newCube(11, 11, 1).move(Coords3d.zOnly(8.5))
        );
        for(int i = 0; i < 3; i++) {
            model = new Union(model,
                newCube(0.5, 6, 2.9).move(new Coords3d(5, 1.5, 3.3 * (i - 1))),
                newCube(8, 0.5, 2.9).move(new Coords3d(0, 5, 3.3 * (i - 1)))
            );
            model = new Difference(model,
                newCube(1, 2, 2).move(new Coords3d(5, 0, 3.3 * (i - 1) + 0.2)),
                newCube(1, 1.4, 2).move(new Coords3d(5, 3, 3.3 * (i - 1) + 0.2))
            );
            if (i > 0) {
                model = new Union(model,
                    newCube(10, 3,0.4).move(new Coords3d( 0, -3.5, 3.3 * (i - 2) - 0.5)),
                    newCube(10, 7,0.4).move(new Coords3d( 0, 1.5, 3.3 * (i - 1) - 1.7))
                );
                model = new Difference(model,
                    i > 1 ? newCube(2, 1, 2).move(new Coords3d(0, 5, 3.3 * (i-1))) : null,
                    newCube(1.4, 1, 2).move(new Coords3d( 2.5, 5, 3.3 * (i-1))),
                    newCube(1.4, 1, 2).move(new Coords3d(-2.5, 5, 3.3 * (i-1)))
                );
            }
        }
        model = new Union(model,
            newCube(0.5, 2.5, 1.45).move(new Coords3d(5, -3, 3.3 * -1 - 1)),
            newCube(0.5, 2.5, 2.9).move(new Coords3d(5, -3, 3.3 * 0 - 1.83)),
            newCube(0.5, 2.5, 2.9).move(new Coords3d(5, -3, 3.3 * 1 - 1.83)),
            newCube(0.5, 2.5, 1.45).move(new Coords3d(5, -3, 3.3 * 1 + 0.83))
        );
        model = new Difference(model,
            // Окна лестницы
            newCube(1, 1.5, 1.5).move(new Coords3d(5, -3, 3.3 * 0 - 1.83)),
            newCube(1, 1.5, 1.5).move(new Coords3d(5, -3, 3.3 * 1 - 1.83)),
            // Окна последнего этажа
            newCube(1, 2, 2).move(new Coords3d(5.5, 0, 3.3 * 2)),
            newCube(1, 1.4, 2).move(new Coords3d(5.5, 3, 3.3 * 2)),
            newCube(1, 1.4, 2).move(new Coords3d(5.5,-3, 3.3 * 2)),
            newCube(2, 1, 2).move(new Coords3d(0, 5.5, 3.3 * 2)),
            newCube(1.4, 1, 2).move(new Coords3d(3, 5.5, 3.3 * 2)),
            newCube(1.4, 1, 2).move(new Coords3d(-3, 5.5, 3.3 * 2)),
            //Дверь на балкон
            newCube(1.4, 1, 2.7).move(new Coords3d(0, 5, -0.14)),
            //Центральный вход
            newCube(2.5, 1, 2.7).move(new Coords3d(0, 5, -3.39))
        );
        model = new Union(model,
            newCube(7, 2, 0.15).move(new Coords3d(0, 6, -1.55)),
            //Подвал
            newCube(10.5, 10.5, 2).move(new Coords3d(0, 0, -6)),
            // Главный вход
            newCube(1, 4.2, 3).move(new Coords3d(-3, 7, -5.5)),
            newCube(1, 4.2, 3).move(new Coords3d( 3, 7, -5.5))
        );
        // Главный вход (ступеньки)
        for(int i = 0; i < 10; i++) {
            model = new Union(model,
                newCube(6, 1.4 + 0.3 * i, 0.2).move(new Coords3d(0, 5.7 + 0.15 * i, -5 - 0.2 * i))
            );
        }
        // Зубья как на замке
        int max = 13;
        for(int i = 0; i < max - 1; i++) {
            double width = (i == 0 ? 0.8 : 0.6);
            double height = (i == 0 ? 1.6 : 1.2);
            model = new Union(model,
                newCube(width, width, height).move(new Coords3d( 5.4,-5.4 + 0.9 * i, 9)),
                newCube(width, width, height).move(new Coords3d(-5.4, 5.4 - 0.9 * i, 9)),
                newCube(width, width, height).move(new Coords3d(-5.4 + 0.9 * i,-5.4, 9)),
                newCube(width, width, height).move(new Coords3d( 5.4 - 0.9 * i, 5.4, 9))
            );
        }
        
        return model;
    }
    private static List<Abstract3dModel> getModels() {
        List<Abstract3dModel> models = new ArrayList<Abstract3dModel>();
        Abstract3dModel mod = FirstTower().move(Coords3d.zOnly(7));
        mod = new Union(mod,
                newCube(10, 10, 10).rotate(new Angles3d(30, 30, 30)).move(new Coords3d(5, 5, 15))
        );
        models.add(mod);
        return models;
    }
}