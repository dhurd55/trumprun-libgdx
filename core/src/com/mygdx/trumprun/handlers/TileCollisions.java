package com.mygdx.trumprun.handlers;
import static com.mygdx.trumprun.handlers.B2dVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TileCollisions
{
    private final World world;
    private final TiledMap map;
    private final int tileSize;

    public TileCollisions(World world, TiledMap map)
    {
        this.world = world;
        this.map = map;
        tileSize = ((TiledMapTileLayer) map.getLayers().get(0)).getTileWidth();

        parseMap();
    }

    private void parseMap()
    {
        MapObjects collisions = map.getLayers().get("Collisions").getObjects();
        for (int i = 0; i < collisions.getCount(); i++)
        {
            MapObject mapObject = collisions.get(i);

            if (mapObject instanceof RectangleMapObject)
            {
                RectangleMapObject rectangleObject = (RectangleMapObject) mapObject;
                Rectangle rectangle = rectangleObject.getRectangle();

                BodyDef bodyDef = getBodyDef(rectangle.getX() + rectangle.getWidth() / 2f, rectangle.getY() + rectangle.getHeight() / 2f);

                //Body body = world.createBody(bodyDef);
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox((rectangle.getWidth() /2f )/ PPM, (rectangle.getHeight() / 2f) / PPM);
                //body.createFixture(polygonShape, 0.0f);
                
				FixtureDef fd = new FixtureDef();
				fd.shape = polygonShape;
				fd.friction = .2f;
				fd.filter.categoryBits = B2dVars.BIT_GROUND;
				fd.filter.maskBits = B2dVars.BIT_PLAYER;
				world.createBody(bodyDef).createFixture(fd);
                polygonShape.dispose();
            }
            else if (mapObject instanceof EllipseMapObject)
            {
                EllipseMapObject circleMapObject = (EllipseMapObject) mapObject;
                Ellipse ellipse = circleMapObject.getEllipse();

                BodyDef bodyDef = getBodyDef(ellipse.x, ellipse.y);

                if (ellipse.width != ellipse.height)
                    throw new IllegalArgumentException("Only circles are allowed.");

                Body body = world.createBody(bodyDef);
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(ellipse.width / 2f);
                body.createFixture(circleShape, 0.0f);
                circleShape.dispose();
            }
            else if (mapObject instanceof PolygonMapObject)
            {
                PolygonMapObject polygonMapObject = (PolygonMapObject) mapObject;
                Polygon polygon = polygonMapObject.getPolygon();

                BodyDef bodyDef = getBodyDef(polygon.getX(), polygon.getY());

                Body body = world.createBody(bodyDef);
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.set(polygon.getVertices());
                body.createFixture(polygonShape, 0.0f);
                polygonShape.dispose();
            }
        }
    }
    private BodyDef getBodyDef(float x, float y)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x/ PPM, y/ PPM);

        return bodyDef;
    }
}
