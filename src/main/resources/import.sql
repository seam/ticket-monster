insert into Document (id) values (1);

insert into Revision (id, document_id, created, createdBy, modified, modifiedBy, content) values (1, 1, '2010-01-01', 'sbryzak', '2010-01-01', 'sbryzak', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin luctus magna sit amet purus vulputate aliquam sed nec ipsum. Phasellus scelerisque enim in arcu tincidunt rhoncus. Ut massa ipsum, feugiat vitae tincidunt ut, fermentum non metus. Ut nulla mauris, lacinia eleifend imperdiet eget, rhoncus in diam. Curabitur lobortis urna sapien, vel ultricies sapien. Cras tristique tellus ipsum, ac lacinia ligula. In hac habitasse platea dictumst. Aenean commodo tempor pharetra. Maecenas fermentum pharetra risus, vitae rhoncus urna feugiat sed. Pellentesque ultricies, ligula id molestie aliquet, arcu enim porttitor velit, sit amet pulvinar odio dui a sapien. Duis vulputate turpis non libero pretium dictum sed in odio. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque feugiat mauris eget eros elementum facilisis ultrices mauris molestie');

update Document set revision_id = 1 where id = 1;

insert into eventcategory (id, description) values (1, 'Concert');
insert into eventcategory (id, description) values (2, 'Theatre');
insert into eventcategory (id, description) values (3, 'Musical');
insert into eventcategory (id, description) values (4, 'Sporting');
insert into eventcategory (id, description) values (5, 'Comedy');

insert into event (id, name, document_id, startDate, endDate, category_id, major) values (1, 'Rock concert of the decade', 1, '2011-01-01', '2011-02-01', 1, true);
