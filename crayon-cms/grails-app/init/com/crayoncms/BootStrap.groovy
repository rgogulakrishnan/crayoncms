package com.crayoncms

import com.crayoncms.Block
import com.crayoncms.Setting
import com.crayoncms.Layout
import com.crayoncms.Menu
import com.crayoncms.MenuGroup
import com.crayoncms.Page
import com.crayoncms.enums.MenuType
import com.crayoncms.enums.PageStatus
import com.crayoncms.enums.BlockType
import com.crayoncms.enums.SettingType
import com.crayoncms.user.Role
import com.crayoncms.user.RoleGroup
import com.crayoncms.user.RoleGroupRole
import com.crayoncms.user.User
import com.crayoncms.user.UserRoleGroup

import grails.plugins.GrailsPluginManager

class BootStrap {

    GrailsPluginManager pluginManager

    def init = { servletContext ->
	
		/********************** Default settings **************************/

        Setting.withTransaction { status ->

            // These are default settings for the website.
            new Setting(name: "Installation status", value: "Fresh", options: "['Fresh', 'Installed']",
                type: SettingType.ADMIN_FIELD, section: "General", mandatory: false).save()

            new Setting(name: "Site Name", description: "The name of the website and title to be used around the site",
                    value: "CrayonCMS Site", type: SettingType.TEXT, section: "General", mandatory: true).save()

            new Setting(name: "Site Status", value: "Open", options: "['Open', 'Closed']",
                    type: SettingType.RADIO, section: "General", mandatory: true).save()

            new Setting(name: "Maintenance Message", value: "Sorry, this website is currently unavailable.",
                    type: SettingType.TEXT_AREA, section: "General", mandatory: true).save()

            new Setting(name: "Time Zone", value: "Asia/Calcutta", type: SettingType.TIME_ZONE_SELECT,
                    section: "General", mandatory: true).save()

            new Setting(name: "Date format", value: "dd MMM, yyyy hh:mm a", type: SettingType.TEXT,
                    section: "General", mandatory: true).save()

            // This is one time setup on boot. Set again on update() in SettingController
            TimeZone.setDefault(TimeZone.getTimeZone(Setting.findBySlug("time-zone").value))
        }

        // By default, these 2 groups will exist
        RoleGroup.withTransaction { status ->
            new RoleGroup(name: "Administrator").save()
            new RoleGroup(name: "Anonymous").save()
        }

        // By default, these 2 users will be there in the system
        User.withTransaction { userStatus ->
            new User(username: 'admin', password: 'password', email: 'admin@test.com', enabled: true).save()
            new User(username: "authenticated", password: "password", email: 'auth@test.com', enabled: true).save()
        }

        // Lets load the roles given in each plugin into the table
        pluginManager.allPlugins.each { plugin ->
            def roles = plugin.getProperties()?.crayonMeta?.roles
            if(roles) {
                roles.each { groupName, rolesList ->
					rolesList.each { key, value ->
						Role.withTransaction { roleStatus ->
							def role = new Role("authority": key, "authorityName": value, "groupName": groupName, "plugin": plugin.getProperties()?.title).save()
							RoleGroupRole.withTransaction { roleGroupRoleStatus ->
								RoleGroupRole.create RoleGroup.findByName("Administrator"), role
							}
						}
					}
                }
            }
        }


        RoleGroupRole.withTransaction { roleGroupRoleStatus ->
            RoleGroupRole.create RoleGroup.findByName("Authenticated"), Role.findByAuthority("ROLE_UPDATE_PROFILE")
            UserRoleGroup.withTransaction { userRoleGroupStatus ->
                UserRoleGroup.create User.findByUsername("authenticated"), RoleGroup.findByName("Authenticated")
            }
        }

        UserRoleGroup.withTransaction { userRoleGroupStatus ->
            UserRoleGroup.create User.findByUsername("admin"), RoleGroup.findByName("Administrator")
        }
		
		/************ end *****************/

        // Remove the below if you don't want the default Helios site

        new Layout(name: "Home", slug: "home", code: '''
<div id="page-wrapper">
  <crayoncms:block name="header" />
  <div class="wrapper style1">
    <div class="container">
      ${content}
    </div>
  </div>
  <crayoncms:block name="footer" />
</div>
''').save()

        new Layout(name: "No Sidebar", slug: "no-sidebar", code: '''
<div id="page-wrapper">
  <crayoncms:block name="header" />
  <div class="wrapper style1">
    <div class="container">
      <article id="main" class="special">
        ${content}
      </article>
    </div>
  </div>
  <crayoncms:block name="footer" />
</div>
''').save()

            new Layout(name: "Right Sidebar", slug: "right-sidebar", code: '''<div id="page-wrapper">
  <crayoncms:block name="header" />
  <div class="wrapper style1">
    <div class="container">
      <div class="row 200%">
        <div class="8u 12u(mobile)" id="content">
          <article id="main">
            ${content}
          </article>
        </div>
        <div class="4u 12u(mobile)" id="sidebar">
          <hr class="first" />
          <crayoncms:block name="sidebar-main" />
          <hr />
          <crayoncms:block name="sidebar-list" />
        </div>
      </div>
    </div>
  </div>
  <crayoncms:block name="footer" />
</div>''').save()

        new Page(
                name: "Home", slug: "home", status: PageStatus.PUBLISHED, layout: Layout.findBySlug("home"),
                description: "home page", keywords: "Grails, CMS, Crayon", bodyCss: "homepage",
                content: '''<section id="features" class="container special">
                    <header>
                        <h2>Morbi ullamcorper et varius leo lacus</h2>
                        <p>Ipsum volutpat consectetur orci metus consequat imperdiet duis integer semper magna.</p>
                    </header>
                    <div class="row">
                        <article class="4u 12u(mobile) special">
                            <a href="#" class="image featured"><img src="/assets/pic07.jpg" alt="" /></a>
                            <header>
                                <h3><a href="#">Gravida aliquam penatibus</a></h3>
                            </header>
                            <p>
                                Amet nullam fringilla nibh nulla convallis tique ante proin sociis accumsan lobortis. Auctor etiam
                                porttitor phasellus tempus cubilia ultrices tempor sagittis. Nisl fermentum consequat integer interdum.
                            </p>
                        </article>
                        <article class="4u 12u(mobile) special">
                            <a href="#" class="image featured"><img src="/assets/pic08.jpg" alt="" /></a>
                            <header>
                                <h3><a href="#">Sed quis rhoncus placerat</a></h3>
                            </header>
                            <p>
                                Amet nullam fringilla nibh nulla convallis tique ante proin sociis accumsan lobortis. Auctor etiam
                                porttitor phasellus tempus cubilia ultrices tempor sagittis. Nisl fermentum consequat integer interdum.
                            </p>
                        </article>
                        <article class="4u 12u(mobile) special">
                            <a href="#" class="image featured"><img src="/assets/pic09.jpg" alt="" /></a>
                            <header>
                                <h3><a href="#">Magna laoreet et aliquam</a></h3>
                            </header>
                            <p>
                                Amet nullam fringilla nibh nulla convallis tique ante proin sociis accumsan lobortis. Auctor etiam
                                porttitor phasellus tempus cubilia ultrices tempor sagittis. Nisl fermentum consequat integer interdum.
                            </p>
                        </article>
                    </div>
                </section>
                ''', roleGroup: RoleGroup.findByName("Anonymous")
        ).save()

        new Page(
                name: "About", slug: "about", status: PageStatus.PUBLISHED, layout: Layout.findBySlug("no-sidebar"),
                description: "about page", keywords: "Grails, CMS, Crayon", bodyCss: "",
                content: '''<header>
    <h2><a href="#">No Sidebar</a></h2>
    <p>
        Morbi convallis lectus malesuada sed fermentum dolore amet
    </p>
</header>
<a href="#" class="image featured"><img src="/assets/pic06.jpg" alt="" /></a>
<p>
    Commodo id natoque malesuada sollicitudin elit suscipit. Curae suspendisse mauris posuere accumsan massa
    posuere lacus convallis tellus interdum. Amet nullam fringilla nibh nulla convallis ut venenatis purus
    lobortis. Auctor etiam porttitor phasellus tempus cubilia ultrices tempor sagittis. Nisl fermentum
    consequat integer interdum integer purus sapien. Nibh eleifend nulla nascetur pharetra commodo mi augue
    interdum tellus. Ornare cursus augue feugiat sodales velit lorem. Semper elementum ullamcorper lacinia
    natoque aenean scelerisque vel lacinia mollis quam sodales congue.
</p>
<section>
    <header>
        <h3>Ultrices tempor sagittis nisl</h3>
    </header>
    <p>
        Nascetur volutpat nibh ullamcorper vivamus at purus. Cursus ultrices porttitor sollicitudin imperdiet
        at pretium tellus in euismod a integer sodales neque. Nibh quis dui quis mattis eget imperdiet venenatis
        feugiat. Neque primis ligula cum erat aenean tristique luctus risus ipsum praesent iaculis. Fermentum elit
        fringilla consequat dis arcu. Pellentesque mus tempor vitae pretium sodales porttitor lacus. Phasellus
        egestas odio nisl duis sociis purus faucibus morbi. Eget massa mus etiam sociis pharetra magna.
    </p>
    <p>
        Eleifend auctor turpis magnis sed porta nisl pretium. Aenean suspendisse nulla eget sed etiam parturient
        orci cursus nibh. Quisque eu nec neque felis laoreet diam morbi egestas. Dignissim cras rutrum consectetur
        ut penatibus fermentum nibh erat malesuada varius.
    </p>
</section>
<section>
    <header>
        <h3>Augue euismod feugiat tempus</h3>
    </header>
    <p>
        Pretium tellus in euismod a integer sodales neque. Nibh quis dui quis mattis eget imperdiet venenatis
        feugiat. Neque primis ligula cum erat aenean tristique luctus risus ipsum praesent iaculis. Fermentum elit
        ut nunc urna volutpat donec cubilia commodo risus morbi. Lobortis vestibulum velit malesuada ante
        egestas odio nisl duis sociis purus faucibus morbi. Eget massa mus etiam sociis pharetra magna.
    </p>
</section>''', roleGroup: RoleGroup.findByName("Anonymous")
        ).save()

        new Page(
                name: "Right Sidebar", slug: "right-sidebar", status: PageStatus.PUBLISHED,
                layout: Layout.findBySlug("right-sidebar"), bodyCss: "",
                description: "right sidebar page", keywords: "Grails, CMS, Crayon",
                content: '''<header>
    <h2><a href="#">Right Sidebar</a></h2>
    <p>
        Morbi convallis lectus malesuada sed fermentum dolore amet
    </p>
</header>
<a href="#" class="image featured"><img src="/assets/pic06.jpg" alt=""></a>
<p>
    Commodo id natoque malesuada sollicitudin elit suscipit. Curae suspendisse mauris posuere accumsan massa
    posuere lacus convallis tellus interdum. Amet nullam fringilla nibh nulla convallis ut venenatis purus
    lobortis. Auctor etiam porttitor phasellus tempus cubilia ultrices tempor sagittis. Nisl fermentum
    consequat integer interdum integer purus sapien. Nibh eleifend nulla nascetur pharetra commodo mi augue
    interdum tellus. Ornare cursus augue feugiat sodales velit lorem. Semper elementum ullamcorper lacinia
    natoque aenean scelerisque vel lacinia mollis quam sodales congue.
</p>
<section>
    <header>
        <h3>Ultrices tempor sagittis nisl</h3>
    </header>
    <p>
        Nascetur volutpat nibh ullamcorper vivamus at purus. Cursus ultrices porttitor sollicitudin imperdiet
        at pretium tellus in euismod a integer sodales neque. Nibh quis dui quis mattis eget imperdiet venenatis
        feugiat. Neque primis ligula cum erat aenean tristique luctus risus ipsum praesent iaculis. Fermentum elit
        fringilla consequat dis arcu. Pellentesque mus tempor vitae pretium sodales porttitor lacus. Phasellus
        egestas odio nisl duis sociis purus faucibus morbi. Eget massa mus etiam sociis pharetra magna.
    </p>
    <p>
        Eleifend auctor turpis magnis sed porta nisl pretium. Aenean suspendisse nulla eget sed etiam parturient
        orci cursus nibh. Quisque eu nec neque felis laoreet diam morbi egestas. Dignissim cras rutrum consectetur
        ut penatibus fermentum nibh erat malesuada varius.
    </p>
</section>
<section>
    <header>
        <h3>Augue euismod feugiat tempus</h3>
    </header>
    <p>
        Pretium tellus in euismod a integer sodales neque. Nibh quis dui quis mattis eget imperdiet venenatis
        feugiat. Neque primis ligula cum erat aenean tristique luctus risus ipsum praesent iaculis. Fermentum elit
        ut nunc urna volutpat donec cubilia commodo risus morbi. Lobortis vestibulum velit malesuada ante
        egestas odio nisl duis sociis purus faucibus morbi. Eget massa mus etiam sociis pharetra magna.
    </p>
</section>''', roleGroup: RoleGroup.findByName("Anonymous")
        ).save()

        new Block(
                name: "Tweets", slug: "tweets", type: BlockType.WYSIWYG, content: '''
<section class="4u 12u(mobile)">
    <header>
        <h2 class="icon fa-twitter circled"><span class="label">Tweets</span></h2>
    </header>
    <ul class="divided">
        <li>
            <article class="tweet">
                Amet nullam fringilla nibh nulla convallis tique ante sociis accumsan.
                <span class="timestamp">5 minutes ago</span>
            </article>
        </li>
        <li>
            <article class="tweet">
                Hendrerit rutrum quisque.
                <span class="timestamp">30 minutes ago</span>
            </article>
        </li>
        <li>
            <article class="tweet">
                Curabitur donec nulla massa laoreet nibh. Lorem praesent montes.
                <span class="timestamp">3 hours ago</span>
            </article>
        </li>
        <li>
            <article class="tweet">
                Lacus natoque cras rhoncus curae dignissim ultricies. Convallis orci aliquet.
                <span class="timestamp">5 hours ago</span>
            </article>
        </li>
    </ul>
</section>'''
        ).save()

        new Block(
                name: "Posts", "slug": "posts", type: BlockType.WYSIWYG, content: '''
<section class="4u 12u(mobile)">
    <header>
        <h2 class="icon fa-file circled"><span class="label">Posts</span></h2>
    </header>
    <ul class="divided">
        <li>
            <article class="post stub">
                <header>
                    <h3><a href="#">Nisl fermentum integer</a></h3>
                </header>
                <span class="timestamp">3 hours ago</span>
            </article>
        </li>
        <li>
            <article class="post stub">
                <header>
                    <h3><a href="#">Phasellus portitor lorem</a></h3>
                </header>
                <span class="timestamp">6 hours ago</span>
            </article>
        </li>
        <li>
            <article class="post stub">
                <header>
                    <h3><a href="#">Magna tempus consequat</a></h3>
                </header>
                <span class="timestamp">Yesterday</span>
            </article>
        </li>
        <li>
            <article class="post stub">
                <header>
                    <h3><a href="#">Feugiat lorem ipsum</a></h3>
                </header>
                <span class="timestamp">2 days ago</span>
            </article>
        </li>
    </ul>
</section>'''
        ).save()

        new Block(
                name: "Photos", slug: "photos", type: BlockType.WYSIWYG, content:'''
<section class="4u 12u(mobile)">
    <header>
        <h2 class="icon fa-camera circled"><span class="label">Photos</span></h2>
    </header>
    <div class="row 25%">
        <div class="6u">
            <a href="#" class="image fit"><img src="/assets/pic10.jpg"></a>
        </div>
        <div class="6u$">
            <a href="#" class="image fit"><img src="/assets/pic11.jpg"></a>
        </div>
        <div class="6u">
            <a href="#" class="image fit"><img src="/assets/pic12.jpg"></a>
        </div>
        <div class="6u$">
            <a href="#" class="image fit"><img src="/assets/pic13.jpg"></a>
        </div>
        <div class="6u">
            <a href="#" class="image fit"><img src="/assets/pic14.jpg"></a>
        </div>
        <div class="6u$">
            <a href="#" class="image fit"><img src="/assets/pic15.jpg"></a>
        </div>
    </div>
</section>'''
        ).save()

        new Block(
                name: "Banner", slug: "banner", type: BlockType.WYSIWYG, content: '''<section id="banner">
  <header>
    <h2>Hi. You're looking at <strong>Helios</strong>.</h2>
    <p>
      A (free) responsive site template by <a href="http://html5up.net">HTML5 UP</a>.
      Built on <strong>skel</strong> and released under the <a href="http://html5up.net/license">CCA</a> license.
    </p>
  </header>
</section>'''
        ).save()

            new Block(name: "Sidebar List", slug: "sidebar-list", type: BlockType.WYSIWYG, content: '''<section>
                                <header>
                                    <h3><a href="#">Sed lorem etiam consequat</a></h3>
                                </header>
                                <p>
                                    Tempus cubilia ultrices tempor sagittis. Nisl fermentum consequat integer interdum.
                                </p>
                                <div class="row 50%">
                                    <div class="4u">
                                        <a href="#" class="image fit"><img src="/assets/pic10.jpg" alt="" /></a>
                                    </div>
                                    <div class="8u">
                                        <h4>Nibh sed cubilia</h4>
                                        <p>
                                            Amet nullam fringilla nibh nulla convallis tique ante proin.
                                        </p>
                                    </div>
                                </div>
                                <div class="row 50%">
                                    <div class="4u">
                                        <a href="#" class="image fit"><img src="/assets/pic11.jpg" alt="" /></a>
                                    </div>
                                    <div class="8u">
                                        <h4>Proin sed adipiscing</h4>
                                        <p>
                                            Amet nullam fringilla nibh nulla convallis tique ante proin.
                                        </p>
                                    </div>
                                </div>
                                <div class="row 50%">
                                    <div class="4u">
                                        <a href="#" class="image fit"><img src="/assets/pic12.jpg" alt="" /></a>
                                    </div>
                                    <div class="8u">
                                        <h4>Lorem feugiat magna</h4>
                                        <p>
                                            Amet nullam fringilla nibh nulla convallis tique ante proin.
                                        </p>
                                    </div>
                                </div>
                                <div class="row 50%">
                                    <div class="4u">
                                        <a href="#" class="image fit"><img src="/assets/pic13.jpg" alt="" /></a>
                                    </div>
                                    <div class="8u">
                                        <h4>Sed tempus fringilla</h4>
                                        <p>
                                            Amet nullam fringilla nibh nulla convallis tique ante proin.
                                        </p>
                                    </div>
                                </div>
                                <div class="row 50%">
                                    <div class="4u">
                                        <a href="#" class="image fit"><img src="/assets/pic14.jpg" alt="" /></a>
                                    </div>
                                    <div class="8u">
                                        <h4>Malesuada fermentum</h4>
                                        <p>
                                            Amet nullam fringilla nibh nulla convallis tique ante proin.
                                        </p>
                                    </div>
                                </div>
                                <footer>
                                    <a href="#" class="button">Magna Adipiscing</a>
                                </footer>
                            </section>''').save()

            new Block(name: "Sidebar Main", slug: "sidebar-main", type: BlockType.WYSIWYG, content: '''<section>
                                <header>
                                    <h3><a href="#">Accumsan sed penatibus</a></h3>
                                </header>
                                <p>
                                    Dolor sed fringilla nibh nulla convallis tique ante proin sociis accumsan lobortis. Auctor etiam
                                    porttitor phasellus tempus cubilia ultrices tempor sagittis  tellus ante diam nec penatibus dolor cras
                                    magna tempus feugiat veroeros.
                                </p>
                                <footer>
                                    <a href="#" class="button">Learn More</a>
                                </footer>
                            </section>''').save()

            new Block(name: 'Header', slug: 'header', type: BlockType.CODE, content: '''
<!-- Header -->
<div id="header">

    <!-- Inner -->
    <div class="inner">
        <header>
            <h1>Hi. You're looking at CrayonCMS.</h1>
            <p>A (free) responsive site template by <a href="http://html5up.net">HTML5 UP</a>.
                     Built on <strong>skel</strong> and released under the <a href="http://html5up.net/license">CCA</a> license.</p>
        </header>
    </div>

    <crayoncms:menu groupName="header" />

</div>

                ''').save()

            new Block(name: 'Meta', slug: 'meta', type: BlockType.CODE, content: '''
<!--
    Helios by HTML5 UP
    html5up.net | @n33co
    Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<head>
    <crayoncms:title prefix="${title}" separator="|" />
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="${description}" />
    <meta name="keywords" content="${keywords}" />
    <!--[if lte IE 8]><asset:javascript src="ie/html5shiv.js" /><![endif]-->
    <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,300italic,400,400italic,600' rel='stylesheet' type='text/css'>
    <asset:stylesheet src="application" />
    <!--[if lte IE 8]><asset:stylesheet src="ie8.css" /><![endif]-->
</head>
                ''').save()

            new Block(name: 'Footer', slug: 'footer', type: BlockType.CODE, content: '''
<div id="footer">
    <div class="container">
        <div class="row">

            <!-- Tweets -->
            <crayoncms:block name="tweets" />

            <!-- Posts -->
            <crayoncms:block name="posts" />

            <!-- Photos -->
            <crayoncms:block name="photos" />

        </div>
        <hr />
        <div class="row">
            <div class="12u">

                <!-- Contact -->
                    <section class="contact">
                        <header>
                            <h3>Nisl turpis nascetur interdum?</h3>
                        </header>
                        <p>Urna nisl non quis interdum mus ornare ridiculus egestas ridiculus lobortis vivamus tempor aliquet.</p>
                        <ul class="icons">
                            <li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
                            <li><a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a></li>
                            <li><a href="#" class="icon fa-instagram"><span class="label">Instagram</span></a></li>
                            <li><a href="#" class="icon fa-pinterest"><span class="label">Pinterest</span></a></li>
                            <li><a href="#" class="icon fa-dribbble"><span class="label">Dribbble</span></a></li>
                            <li><a href="#" class="icon fa-linkedin"><span class="label">Linkedin</span></a></li>
                        </ul>
                    </section>

                    <!-- Copyright -->
                    <div class="copyright">
                        <ul class="menu">
                            <li>&copy; Untitled. All rights reserved.</li><li>Design: <a href="http://html5up.net" target="_blank">HTML5 UP</a></li>
                            <li>Images: <a href="http://unsplash.com" target="_blank">Unsplash</a></li>
                        </ul>
                    </div>

            </div>

        </div>
    </div>
</div>
                ''').save()

            new Block(name: 'Scripts', slug: 'scripts', type: BlockType.CODE, content: '''
<!-- Scripts -->
<asset:javascript src="application" />
<!--[if lte IE 8]><asset:javascript src="ie/respond.min.js" /><![endif]-->
                ''').save()

        def header = MenuGroup.findOrSaveWhere(name: "Header")
        def footer = MenuGroup.findOrSaveWhere(name: "Footer")

        Menu.findOrSaveWhere(name: "Home", menuType: MenuType.PAGE, menuTypeValue: "/home", menuGroup: header,
                position: 1, parent:0, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "Dropdown", menuType: MenuType.PAGE, menuTypeValue: "#", menuGroup: header,
                position: 2, parent:0, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "Right Sidebar", menuType: MenuType.PAGE, menuTypeValue: "/right-sidebar", menuGroup: header,
                position: 3, parent:0, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "About", menuType: MenuType.PAGE, menuTypeValue: "/about", menuGroup: header,
                position: 4, parent: 0, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "Login", menuType: MenuType.LOGIN, menuTypeValue: "/login", menuGroup: header,
                position: 5, parent: 0, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "Admin", menuType: MenuType.LOGOUT, menuTypeValue: "/admin", menuGroup: header,
                position: 6, parent: 0, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "Logout", menuType: MenuType.LOGOUT, menuTypeValue: "/logout", menuGroup: header,
                position: 7, parent: 0, cssClass: "", access: "", openInNewTab: "No")

        Menu.findOrSaveWhere(name: "Lorem ipsum dolor", menuType: MenuType.PAGE, menuTypeValue: "#", menuGroup: header,
                position: 1, parent: 2, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "Magna phasellus", menuType: MenuType.PAGE, menuTypeValue: "#", menuGroup: header,
                position: 2, parent: 2, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "Etiam dolore nisl", menuType: MenuType.PAGE, menuTypeValue: "#", menuGroup: header,
                position: 3, parent: 2, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "And a submenu &hellip;", menuType: MenuType.PAGE, menuTypeValue: "#", menuGroup: header,
                position: 4, parent: 2, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "Veroeros feugiat", menuType: MenuType.PAGE, menuTypeValue: "#", menuGroup: header,
                position: 5, parent: 2, cssClass: "", access: "", openInNewTab: "No")

        Menu.findOrSaveWhere(name: "Lorem ipsum dolor", menuType: MenuType.PAGE, menuTypeValue: "#", menuGroup: header,
                position: 1, parent: 10, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "Magna phasellus", menuType: MenuType.PAGE, menuTypeValue: "#", menuGroup: header,
                position: 2, parent: 10, cssClass: "", access: "", openInNewTab: "No")
        Menu.findOrSaveWhere(name: "Phasellus consequat", menuType: MenuType.PAGE, menuTypeValue: "#", menuGroup: header,
                position: 3, parent: 10, cssClass: "", access: "", openInNewTab: "No")

    }
    def destroy = {
    }
}
