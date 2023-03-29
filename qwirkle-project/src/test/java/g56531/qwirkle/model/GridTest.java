package g56531.qwirkle.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static g56531.qwirkle.model.Color.*;
import static g56531.qwirkle.model.Direction.*;
import static g56531.qwirkle.model.Shape.*;
import static g56531.qwirkle.model.QwirkleTestUtils.*;
public class GridTest {
    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid();
    }

    @Test
    void firstAdd_one_tile() {
        var tile = new Tile(BLUE, CROSS);
        grid.firstAdd(RIGHT, tile);
        assertSame(get(grid, 0, 0), tile);
    }

    @Test
    void firstAdd_several_different_shape_tile() {
        var tile = new Tile(BLUE, CROSS);
        var tile2 = new Tile(BLUE, SQUARE);
        grid.firstAdd(RIGHT, tile, tile2);
        assertSame(get(grid, 0, 1), tile2);
    }

    @Test
    void firstAdd_several_different_colors_tile() {
        var tile = new Tile(BLUE, CROSS);
        var tile2 = new Tile(RED, CROSS);
        grid.firstAdd(RIGHT, tile, tile2);
        assertSame(get(grid, 0, 1), tile2);
    }

    @Test
    void firstAdd_several_same_shape_tile() {
        var tile = new Tile(BLUE, CROSS);
        var tile2 = new Tile(BLUE, CROSS);
        assertThrows(QwirkleException.class, () ->{
            grid.firstAdd(RIGHT, tile, tile2);
        });
    }

    @Test
    void firstAdd_several_same_color_tile() {
        var tile = new Tile(BLUE, CROSS);
        var tile2 = new Tile(BLUE, CROSS);
        assertThrows(QwirkleException.class, () ->{
            grid.firstAdd(RIGHT, tile, tile2);
        });
    }
    @Test
    void rules_sonia_a() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        assertEquals(t1, get(grid, 0, 0));
        assertEquals(t2, get(grid, -1, 0));
        assertEquals(t3, get(grid, -2, 0));
    }

    @Test
    void rules_sonia_a_adapted_to_fail() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, DIAMOND);
        assertThrows(QwirkleException.class, ()->{
            grid.firstAdd(UP, t1, t2, t3);
        });
        assertNull(get(grid,0,0));
        assertNull(get(grid,-1,0));
        assertNull(get(grid,-2,0));
    }

    @Test
    void firstAdd_cannot_be_called_twice() {
        Tile redcross = new Tile(RED, CROSS);
        Tile reddiamond = new Tile(RED, DIAMOND);
        grid.firstAdd(UP, redcross, reddiamond);
        assertThrows(QwirkleException.class, () -> grid.firstAdd(DOWN, redcross, reddiamond));
    }

    @Test
    void firstAdd_must_be_called_first_simple() {
        Tile redcross = new Tile(RED, CROSS);
        assertThrows(QwirkleException.class, () -> add(grid, 0, 0, redcross));
    }

   @Test
    void add_one_tile_same_shape() {
       var t1 = new Tile(RED, ROUND);
       var t2 = new Tile(RED, DIAMOND);
       var t3 = new Tile(RED, PLUS);
       grid.firstAdd(UP, t1, t2, t3);
       var t4 = new Tile(BLUE,ROUND);
       grid.add(45, 44, t4);
       assertEquals(t4, grid.get(45, 44));

   }

    @Test
    void add_one_tile() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED,SQUARE);
        grid.add(44, 46, t4);
        assertEquals(t4, get(grid, -1, 1));

    }

    @Test
    void add_one_tile_on_tile() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(BLUE,ROUND);
        assertThrows(QwirkleException.class, () -> add(grid, 0, 0, t4));
    }

    @Test
    void add_one_tile_no_valid_move() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(BLUE,ROUND);
        grid.add(44,44, t4);
        assertNull(get(grid, -1, -1));
    }

    @Test
    void add_one_tile_no_valid_move_anything_tile_around() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(BLUE,ROUND);
        grid.add(46,44, t4);
        assertNull(get(grid, 1, -1));
    }

    @Test
    void rules_cedric_b() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(RED, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        assertNull(grid.get(46,45));
        //assertEquals(t4, get(grid, 1, 0));
        //assertEquals(t5, get(grid, 1, 1));
        //assertEquals(t6, get(grid, 1, 2));
    }

}