package g56531.qwirkle.model;


import g56531.qwirkle.view.View;
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
        var tile2 = new Tile(ORANGE, SQUARE);
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
    void tile_is_comptabile_with_tile_near_but_not_with_the_other_Tile_in_the_line() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(GREEN,PLUS);
        grid.add(42, 45, t4);
        assertNull(grid.get(42, 45));

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
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        assertEquals(t4, get(grid, 1, 0));
        assertEquals(t5, get(grid, 1, 1));
        assertEquals(t6, get(grid, 1, 2));
    }

    @Test
    void rules_cedric_b_wrong_same_tile() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(RED, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        assertNull(grid.get(46,45));
    }

    @Test
    void rules_elvire_c() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        assertEquals(t7, get(grid, 0, 1));
    }

    @Test
    void rules_vincent_d() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        assertEquals(t8, grid.get(43,44));
        assertEquals(t9, grid.get( 44, 44));
    }

    @Test
    void rules_sonia_e() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        assertEquals(t10.tile(), grid.get(42,44));
        assertEquals(t11.tile(), grid.get( 45, 44));
    }

    @Test
    void rules_sonia_e_second_tile_wrong() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(BLUE, ROUND));
        grid.add(t10, t11);
        assertNull(grid.get(42,44));
        assertNull(grid.get( 45, 44));
    }


    @Test
    void rules_cedric_f() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        assertEquals(t12, grid.get(46,48));
        assertEquals(t13, grid.get( 47, 48));
    }
    @Test
    void rules_cedric_f_wrong_first_tile() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(BLUE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        assertNull(grid.get(46,48));
        assertNull(grid.get( 47, 48));
    }

    @Test
    void rules_elvire_g() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        assertEquals(t14, grid.get(42,43));
        assertEquals(t15, grid.get( 42, 42));
    }

    @Test
    void rules_elvire_g_wrong_same_color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(GREEN, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        assertNull(grid.get(42,43));
        assertNull(grid.get( 42, 42));
    }

    @Test
    void rules_vincent_h() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        grid.add(43, 42, DOWN, t16, t17);
        assertEquals(t16, grid.get(43,42));
        assertEquals(t17, grid.get( 44, 42));
    }

    @Test
    void rules_vincent_h_wrong_color() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(YELLOW, DIAMOND);
        grid.add(43, 42, DOWN, t16, t17);
        assertNull(grid.get(43,42));
        assertNull(grid.get( 44, 42));
    }

    @Test
    void rules_sonia_i() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        grid.add(43, 42, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        grid.add(44, 43, DOWN, t18, t19);
        assertEquals(t18, grid.get(44,43));
        assertEquals(t19, grid.get( 45, 43));
    }

    @Test
    void rules_sonia_i_wrong_shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        grid.add(43, 42, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, SQUARE);
        grid.add(44, 43, DOWN, t18, t19);
        assertNull(grid.get(44,43));
        assertNull(grid.get( 45, 43));
    }

    @Test
    void rules_cedric_j() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        grid.add(43, 42, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        grid.add(44, 43, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        grid.add(42, 45, t20);
        assertEquals(t20, grid.get(42,45));
    }

    @Test
    void rules_cedric_j_with_double() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        grid.add(43, 42, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        grid.add(44, 43, DOWN, t18, t19);
        var t20 = new Tile(ORANGE, STAR);
        grid.add(42, 45, t20);
        assertNull(grid.get(42,45));
    }

    @Test
    void rules_elvire_k() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        grid.add(43, 42, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        grid.add(44, 43, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        grid.add(42, 45, t20);
        var t21 = new TileAtPosition(47, 45, new Tile(RED, CROSS));
        var t22 = new TileAtPosition(47, 46, new Tile(BLUE, CROSS));
        var t23 = new TileAtPosition(47, 44, new Tile(ORANGE, CROSS));
        grid.add(t21,t22,t23);
        assertEquals(t21.tile(), grid.get(47,45));
        assertEquals(t22.tile(), grid.get(47,46));
        assertEquals(t23.tile(), grid.get(47,44));
    }

    @Test
    void rules_elvire_k_with_double_extrmeite() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        grid.add(43, 42, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        grid.add(44, 43, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        grid.add(42, 45, t20);
        var t21 = new TileAtPosition(47, 45, new Tile(RED, STAR));
        var t22 = new TileAtPosition(47, 46, new Tile(BLUE, CROSS));
        var t23 = new TileAtPosition(47, 44, new Tile(ORANGE, CROSS));
        grid.add(t21,t22,t23);
        assertNull(grid.get(47,45));
        assertNull(grid.get(47,46));
        assertNull(grid.get(47,44));
    }

    @Test
    void rules_vincent_l() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        grid.add(43, 42, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        grid.add(44, 43, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        grid.add(42, 45, t20);
        var t21 = new TileAtPosition(47, 45, new Tile(RED, CROSS));
        var t22 = new TileAtPosition(47, 46, new Tile(BLUE, CROSS));
        var t23 = new TileAtPosition(47, 44, new Tile(ORANGE, CROSS));
        grid.add(t21,t22,t23);
        var t24 = new Tile(YELLOW, SQUARE);
        var t25 = new Tile(BLUE, SQUARE);
        grid.add(46,49, DOWN, t24, t25);
        assertEquals(t24, grid.get(46,49));
        assertEquals(t25, grid.get(47,49));

    }

    @Test
    void rules_vincent_l_wrong_shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(BLUE, SQUARE);
        var t6 = new Tile(PURPLE, SQUARE);
        grid.add(46,45,RIGHT, t4, t5, t6);
        var t7 = new Tile(BLUE, ROUND);
        grid.add(45,46, t7);
        var t8 = new Tile(GREEN, PLUS);
        var t9 = new Tile(GREEN, DIAMOND);
        grid.add(43,44, DOWN, t8, t9);
        var t10 = new TileAtPosition(42,44, new Tile(GREEN, STAR));
        var t11 = new TileAtPosition(45,44, new Tile(GREEN, ROUND));
        grid.add(t10, t11);
        var t12 = new Tile(ORANGE, SQUARE);
        var t13 = new Tile(RED, SQUARE);
        grid.add(46, 48, DOWN, t12, t13);
        var t14 = new Tile(YELLOW, STAR);
        var t15 = new Tile(ORANGE, STAR);
        grid.add(42, 43, LEFT, t14, t15);
        var t16 = new Tile(ORANGE, CROSS);
        var t17 = new Tile(ORANGE, DIAMOND);
        grid.add(43, 42, DOWN, t16, t17);
        var t18 = new Tile(YELLOW, DIAMOND);
        var t19 = new Tile(YELLOW, ROUND);
        grid.add(44, 43, DOWN, t18, t19);
        var t20 = new Tile(RED, STAR);
        grid.add(42, 45, t20);
        var t21 = new TileAtPosition(47, 45, new Tile(RED, CROSS));
        var t22 = new TileAtPosition(47, 46, new Tile(BLUE, CROSS));
        var t23 = new TileAtPosition(47, 44, new Tile(ORANGE, CROSS));
        grid.add(t21,t22,t23);
        var t24 = new Tile(YELLOW, SQUARE);
        var t25 = new Tile(BLUE, ROUND);
        grid.add(46,49, DOWN, t24, t25);
        assertNull(grid.get(46,49));
        assertNull(grid.get(47,49));
    }

}